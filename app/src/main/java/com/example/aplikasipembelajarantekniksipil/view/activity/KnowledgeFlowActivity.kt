package com.example.aplikasipembelajarantekniksipil.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.adapter.KnowledgeAdapter
import com.example.aplikasipembelajarantekniksipil.database.DatabaseAccess
import com.example.aplikasipembelajarantekniksipil.model.KnowledgeModel
import com.example.aplikasipembelajarantekniksipil.presenter.KnowledgePresenter
import com.example.aplikasipembelajarantekniksipil.view.view_interface.KnowledgeView
import kotlinx.android.synthetic.main.activity_knowledge_flow.*

class KnowledgeFlowActivity : AppCompatActivity(),KnowledgeView {

    private var knowledgesData:ArrayList<KnowledgeModel> = arrayListOf()
    private lateinit var knowledgeAdapter: KnowledgeAdapter
    private lateinit var knowledgePresenter: KnowledgePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge_flow)


        val chapterData: Int = intent.extras!!.getInt("CHAPTER_ID",0)
        Log.d(">>>>>ChapterData",chapterData.toString())
        Log.d(">>>>>>>ChapterID ", chapterData.toString())

        val databaseAccess:DatabaseAccess = DatabaseAccess.getInstance(this)
        Log.d(">>>>>KnowledgeFlow","sucess instance database access")
        knowledgeAdapter = KnowledgeAdapter(this,knowledgesData)
        Log.d(">>>>>KnowledgeFlow","sucess instance an adapter")
        knowledgePresenter = KnowledgePresenter(this)
        Log.d(">>>>>KnowledgeFlow","sucess intance a presenter")
        knowledgePresenter.setKnowledge(databaseAccess,chapterData)
        Log.d(">>>>>KnowledgeFlow","sucess gathering data")

        rv_knowledge_flow.layoutManager = LinearLayoutManager(this)
        rv_knowledge_flow.setHasFixedSize(true)
        rv_knowledge_flow.adapter = knowledgeAdapter
    }

    override fun showKnowledge(knowledges: List<KnowledgeModel>) {
        this.knowledgesData.addAll(knowledges)
        knowledgeAdapter.notifyDataSetChanged()
    }
}
