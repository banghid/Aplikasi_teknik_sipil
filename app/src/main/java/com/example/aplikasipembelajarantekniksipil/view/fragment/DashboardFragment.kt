package com.example.aplikasipembelajarantekniksipil.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.adapter.ChapterAdapter
import com.example.aplikasipembelajarantekniksipil.database.DatabaseAccess
import com.example.aplikasipembelajarantekniksipil.model.ChapterModel
import com.example.aplikasipembelajarantekniksipil.presenter.ChapterPresenter
import com.example.aplikasipembelajarantekniksipil.view.view_interface.ChapterView
import kotlinx.android.synthetic.main.activity_main.*



class DashboardFragment : Fragment(),ChapterView {
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
        chapterAdapter = ChapterAdapter(view.context,chaptersData)
        chapterPresenter = ChapterPresenter(this)
        chapterPresenter.setChapter(databaseAccess)

        rv_chapter.layoutManager =
                LinearLayoutManager(view.context)
        rv_chapter.setHasFixedSize(true)
        rv_chapter.adapter = chapterAdapter

    }

    override fun showChapter(chapters: List<ChapterModel>) {
        this.chaptersData.addAll(chapters)
        chapterAdapter.notifyDataSetChanged()
    }


}
