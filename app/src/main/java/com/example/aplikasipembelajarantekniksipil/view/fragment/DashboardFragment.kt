package com.example.aplikasipembelajarantekniksipil.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.adapter.ChapterAdapter
import com.example.aplikasipembelajarantekniksipil.database.DatabaseAccess
import com.example.aplikasipembelajarantekniksipil.model.ChapterModel
import com.example.aplikasipembelajarantekniksipil.presenter.ChapterPresenter
import com.example.aplikasipembelajarantekniksipil.view.view_interface.ChapterView
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment(), ChapterView {
    private var chaptersData: ArrayList<ChapterModel> = arrayListOf()
    private lateinit var chapterPresenter: ChapterPresenter
    private lateinit var chapterAdapter: ChapterAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val databaseAccess: DatabaseAccess = DatabaseAccess.getInstance(view.context)
        chapterAdapter = ChapterAdapter(view.context, chaptersData)
        chapterPresenter = ChapterPresenter(this)
        chapterPresenter.setChapter(databaseAccess)

        rv_chapter.layoutManager =
            LinearLayoutManager(view.context)
        rv_chapter.setHasFixedSize(true)
        rv_chapter.adapter = chapterAdapter


    }

    override fun showChapter(chapters: List<ChapterModel>) {
        this.chaptersData.clear()
        this.chaptersData.addAll(chapters)
        chapterAdapter.notifyDataSetChanged()
    }

//    private fun showPrompt() {
//      val prefManager = PreferenceManager.getDefaultSharedPreferences(view?.context)
//
//     if (!prefManager.getBoolean("masterPrompt", false)) {
//
//        val target:View? = view?.findViewById(R.id.rv_chapter)
//            MaterialTapTargetPrompt.Builder(this)
//                .setTarget(target)
//                .setPrimaryText("Navigation Button")
//                .setSecondaryText("Tab untuk menampilkan menu pada aplikasi")
//                .setBackButtonDismissEnabled(true)
//                .setClipToView(null)
//                .setPromptStateChangeListener { prompt, state ->
//                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
//                        state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
//                    ) {
//                     val prefEditor = prefManager.edit()
//                      prefEditor.putBoolean("masterPrompt", true)
//                       prefEditor.apply()
//                        showChapterPrompt()
//                    }
//                }.show()
//      }
//    }
//
//    private fun showChapterPrompt() {
//        MaterialTapTargetPrompt.Builder(this as Fragment)
//            .setTarget(R.id.rv_chapter)
//            .setPrimaryText("List Materi")
//            .setSecondaryText("Klik salah satu materi untuk menampilkan materi!")
//            .setBackButtonDismissEnabled(true)
//            .setPromptBackground(RectanglePromptBackground())
//            .setPromptFocal(RectanglePromptFocal())
//            .setClipToView(null)
//            .show()
//    }


}
