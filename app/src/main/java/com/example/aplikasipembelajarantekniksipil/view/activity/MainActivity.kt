package com.example.aplikasipembelajarantekniksipil.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.adapter.ChapterAdapter
import com.example.aplikasipembelajarantekniksipil.database.DatabaseAccess
import com.example.aplikasipembelajarantekniksipil.model.ChapterModel
import com.example.aplikasipembelajarantekniksipil.presenter.ChapterPresenter
import com.example.aplikasipembelajarantekniksipil.view.view_interface.ChapterView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ChapterView {

    private var chaptersData: ArrayList<ChapterModel> = arrayListOf()
    private lateinit var chapterPresenter: ChapterPresenter
    private lateinit var chapterAdapter: ChapterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val databaseAccess:DatabaseAccess = DatabaseAccess.getInstance(this)
        chapterAdapter = ChapterAdapter(this,chaptersData)
        chapterPresenter = ChapterPresenter(this)
        chapterPresenter.setChapter(databaseAccess)

        rv_chapter.layoutManager = LinearLayoutManager(this)
        rv_chapter.setHasFixedSize(true)
        rv_chapter.adapter = chapterAdapter
    }

    override fun showChapter(chapters: List<ChapterModel>) {
        this.chaptersData.addAll(chapters)
        chapterAdapter.notifyDataSetChanged()
    }

}
