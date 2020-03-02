package com.example.aplikasipembelajarantekniksipil.view.activity

import android.os.Bundle
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

//        showPrompt()

    }

    override fun showKnowledge(knowledges: List<KnowledgeModel>) {
        this.knowledgesData.addAll(knowledges)
    }


//    private fun showPrompt(){
//        val prefManager = PreferenceManager.getDefaultSharedPreferences(this)
//
//        if (!prefManager.getBoolean("FLOW_TURORIAL", false)) {
//            MaterialTapTargetPrompt.Builder(this)
//                .setTarget(recyclerView.getChildAt(1))
//                .setPrimaryText("Sub BAB dalam Materi")
//                .setSecondaryText("Klik tombol 'Pelajari' untuk mulai belajar.")
//                .setBackButtonDismissEnabled(true)
//                .setPromptStateChangeListener { prompt, state ->
//                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
//                        state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
//                    ) {
//                        val prefEditor = prefManager.edit()
//                        prefEditor.putBoolean("FLOW_TURORIAL", true)
//                        prefEditor.apply()
//                        showNext()
//                    }
//                }
//                .setPromptBackground(RectanglePromptBackground())
//                .setPromptFocal(RectanglePromptFocal())
//                .show()
//        }
//    }

//    private fun showNext(){
//        MaterialTapTargetPrompt.Builder(this)
//            .setTarget(recyclerView.getChildAt(2))
//            .setPrimaryText("Sub BAB Materi berupa Quiz")
//            .setSecondaryText("Klik tombol 'Kerjakan' untuk mulai mengerjakan.")
//            .setBackButtonDismissEnabled(true)
//            .setPromptBackground(RectanglePromptBackground())
//            .setPromptFocal(RectanglePromptFocal())
//            .show()
//    }
}
