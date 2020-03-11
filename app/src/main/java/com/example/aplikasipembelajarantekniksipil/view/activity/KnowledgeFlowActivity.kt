package com.example.aplikasipembelajarantekniksipil.view.activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.adapter.KnowledgeAdapter
import com.example.aplikasipembelajarantekniksipil.database.DatabaseAccess
import com.example.aplikasipembelajarantekniksipil.model.KnowledgeModel
import com.example.aplikasipembelajarantekniksipil.presenter.KnowledgePresenter
import com.example.aplikasipembelajarantekniksipil.view.view_interface.KnowledgeView
import kotlinx.android.synthetic.main.activity_knowledge_flow.*
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal

class KnowledgeFlowActivity : AppCompatActivity(), KnowledgeView {

    private var knowledgesData: ArrayList<KnowledgeModel> = arrayListOf()
    private lateinit var knowledgeAdapter: KnowledgeAdapter
    private lateinit var knowledgePresenter: KnowledgePresenter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge_flow)

        recyclerView = rv_knowledge_flow


        if (savedInstanceState?.getParcelableArrayList<KnowledgeModel>("data") != null) {
            val dataTemp =
                ArrayList<KnowledgeModel>(savedInstanceState.getParcelableArrayList("data")!!)
            knowledgeAdapter = KnowledgeAdapter(
                this,
                dataTemp
            )
            knowledgeAdapter.notifyDataSetChanged()
        } else {
            val chapterData = intent?.extras?.getInt("CHAPTER_ID")

            Log.d(">>>>>ChapterData", chapterData.toString())
            Log.d(">>>>>>>ChapterID ", chapterData.toString())

            val databaseAccess: DatabaseAccess = DatabaseAccess.getInstance(this)
            Log.d(">>>>>KnowledgeFlow", "success instance database access")
            knowledgeAdapter = KnowledgeAdapter(this, knowledgesData)
            Log.d(">>>>>KnowledgeFlow", "success instance an adapter")
            knowledgePresenter = KnowledgePresenter(this)
            Log.d(">>>>>KnowledgeFlow", "success instance a presenter")
            knowledgePresenter.setKnowledge(databaseAccess, chapterData)
            Log.d(">>>>>KnowledgeFlow", "success gathering data")
        }

        rv_knowledge_flow.layoutManager =
            LinearLayoutManager(this)
        rv_knowledge_flow.setHasFixedSize(true)
        rv_knowledge_flow.adapter = knowledgeAdapter

        kompetensi_button.setOnClickListener {
            val kompetensiKayu = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Document</title>\n" +
                    "    <style>\n" +
                    "        p {\n" +
                    "          text-indent: 20px;\n" +
                    "        }\n" +
                    "        \n" +
                    "        li {\n" +
                    "          text-align: justify;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "    <h3 style=\"text-align: center;\">\n" +
                    "        Kompetensi Dasar Kayu\n" +
                    "    </h3>\n" +
                    "\n" +
                    "    <ul type=\"A\">\n" +
                    "        <li>\n" +
                    "            Kompetensi Dasar\n" +
                    "            <br>\n" +
                    "            3.3 Memahami spesifikasi dan karakteristik kayu\n" +
                    "            <br>\n" +
                    "            4.3 Mempresentasikan spesifikasi dan karakteristik kayu\n" +
                    "               \n" +
                    "        </li>\n" +
                    "        <br>\n" +
                    "        <li>\n" +
                    "            Indikator Pencapaian Kompetensi\n" +
                    "            <br>\n" +
                    "            3.3.1 Menjelaskan definisi kayu\n" +
                    "            <br>\n" +
                    "            3.3.2 Menjelaskan jenis-jenis kayu\n" +
                    "            <br>\n" +
                    "            3.3.3 Menjelaskan sifat kayu\n" +
                    "            <br>\n" +
                    "            3.3.4. menjelaskan mutu dan kelas kayu\n" +
                    "        </li>        \n" +
                    "        <br>\n" +
                    "        <li>\n" +
                    "            Tujuan Pembelajaran\n" +
                    "            <br>\n" +
                    "            Setelah mempelajari materi yang terdapat didalam aplikasi ini, pengguna dapat memahami spesifikasi dan karakteristik kayu dengan baik dan benar.\n" +
                    "        </li>\n" +
                    "    </ul>\n" +
                    "    \n" +
                    "</body>\n" +
                    "</html>"

            val kompetensiBeton = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Document</title>\n" +
                    "    <style>\n" +
                    "        p {\n" +
                    "          text-indent: 20px;\n" +
                    "        }\n" +
                    "        \n" +
                    "        li {\n" +
                    "          text-align: justify;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "    <h3 style=\"text-align: center;\">\n" +
                    "        Kompetensi Dasar Beton\n" +
                    "    </h3>\n" +
                    "\n" +
                    "    <ul type=\"A\">\n" +
                    "        <li>\n" +
                    "            Kompetensi Dasar\n" +
                    "            <br>\n" +
                    "            3.4 Memahami spesifikasi dan karakteristik beton\n" +
                    "            <br>\n" +
                    "            4.4 Mempresentasikan spesifikasi dan karakteristik beton\n" +
                    "               \n" +
                    "        </li>\n" +
                    "        <br>\n" +
                    "        <li>\n" +
                    "            Indikator Pencapaian Kompetensi\n" +
                    "            <br>\n" +
                    "            3.4.1 Menjelaskan definisi beton\n" +
                    "            <br>\n" +
                    "            3.4.2 Menjelaskan kelebihan, kelemahan, dan sifat beton\n" +
                    "            <br>\n" +
                    "            3.4.3 Menjelaskan bahan penyusun beton\n" +
                    "            <br>\n" +
                    "            3.4.4. menjelaskan beton bertulang\n" +
                    "\n" +
                    "\n" +
                    "        </li>        \n" +
                    "        <br>\n" +
                    "        <li>\n" +
                    "            Tujuan Pembelajaran\n" +
                    "            <br>\n" +
                    "            Setelah mempelajari materi yang terdapat didalam aplikasi ini, pengguna dapat memahami spesifikasi dan karakteristik kayu dengan baik dan benar.\n" +
                    "        </li>\n" +
                    "    </ul>\n" +
                    "    \n" +
                    "</body>\n" +
                    "</html>"

            val kompetensiBaja = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Document</title>\n" +
                    "    <style>\n" +
                    "        p {\n" +
                    "          text-indent: 20px;\n" +
                    "        }\n" +
                    "        \n" +
                    "        li {\n" +
                    "          text-align: justify;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "    <h3 style=\"text-align: center;\">\n" +
                    "        Kompetensi Dasar Baja\n" +
                    "    </h3>\n" +
                    "\n" +
                    "    <ul type=\"A\">\n" +
                    "        <li>\n" +
                    "            Kompetensi Dasar\n" +
                    "            <br>\n" +
                    "            3.3 Memahami spesifikasi dan karakteristik beton\n" +
                    "            <br>\n" +
                    "            4.3 Mempresentasikan spesifikasi dan karakteristik beton\n" +
                    "               \n" +
                    "        </li>\n" +
                    "        <br>\n" +
                    "        <li>\n" +
                    "            Indikator Pencapaian Kompetensi\n" +
                    "            <br>\n" +
                    "            3.3.1 Menjelaskan definisi baja\n" +
                    "            <br>\n" +
                    "            3.3.2 Menjelaskan sifat, kelemahan, dan kelebihan baja\n" +
                    "            <br>\n" +
                    "            3.3.3 Menjelaskan baja ringan\n" +
                    "\n" +
                    "        </li>        \n" +
                    "        <br>\n" +
                    "        <li>\n" +
                    "            Tujuan Pembelajaran\n" +
                    "            <br>\n" +
                    "            Setelah mempelajari materi yang terdapat didalam aplikasi ini, pengguna dapat memahami spesifikasi dan karakteristik kayu dengan baik dan benar.\n" +
                    "        </li>\n" +
                    "    </ul>\n" +
                    "    \n" +
                    "</body>\n" +
                    "</html>"

            val kompetensiIntent = Intent(this,KompetensiActivity::class.java)
            val chapterData = intent?.extras?.getInt("CHAPTER_ID")
            when(chapterData){
                1 ->{
                    kompetensiIntent.putExtra("KOMPETENSI_DASAR", kompetensiKayu)
                }
                2 ->{
                    kompetensiIntent.putExtra("KOMPETENSI_DASAR", kompetensiBeton)
                }
                3 ->{
                    kompetensiIntent.putExtra("KOMPETENSI_DASAR", kompetensiBaja)
                }
            }

            startActivity(kompetensiIntent)
        }

        showPrompt()

    }

    override fun showKnowledge(knowledges: List<KnowledgeModel>) {
        this.knowledgesData.addAll(knowledges)
    }


    private fun showPrompt(){
        val prefManager = PreferenceManager.getDefaultSharedPreferences(this)

        if (!prefManager.getBoolean("FLOW_TURORIAL", false)) {
            MaterialTapTargetPrompt.Builder(this)
                .setTarget(kompetensi_button)
                .setPrimaryText("Kompetensi Dasar")
                .setSecondaryText("Klik tombol 'Kompetensi Dasar' untuk menampilkan Kompetensi dasar BAB.")
                .setBackButtonDismissEnabled(true)
                .setPromptStateChangeListener { prompt, state ->
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                        state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
                    ) {
                        val prefEditor = prefManager.edit()
                        prefEditor.putBoolean("FLOW_TURORIAL", true)
                        prefEditor.apply()
                    }
                }
                .setPromptBackground(RectanglePromptBackground())
                .setPromptFocal(RectanglePromptFocal())
                .show()
        }
    }


}
