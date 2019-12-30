package com.example.aplikasipembelajarantekniksipil.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.model.KnowledgeModel
import com.example.aplikasipembelajarantekniksipil.view.activity.KnowledgeDetailActivity
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.android.synthetic.main.knowledge_item.view.*


class KnowledgeAdapter(private var context: Context, private var knowledgeList: ArrayList<KnowledgeModel>):RecyclerView.Adapter<KnowledgeHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): KnowledgeHolder {
        return KnowledgeHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.knowledge_item,
                p0,
                false
            ),
            p1
        )
    }

    override fun getItemCount(): Int = knowledgeList.size

    override fun onBindViewHolder(p0: KnowledgeHolder, p1: Int) {
        p0.bindKnowledge(context,knowledgeList[p1])
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position,itemCount)
    }

}

class KnowledgeHolder(view: View, viewType:Int):RecyclerView.ViewHolder(view){

    private var knowledgeTitle = view.knowledge_title
    private var knowledgeCaption = view.knowledge_caption
    private var knowledgeButton = view.knowledge_button
    private var knowledgeTimeline = view.knowledge_timeline

    init {
        knowledgeTimeline.initLine(viewType)
    }

    fun bindKnowledge(context: Context, knowledgeData: KnowledgeModel){
        knowledgeTitle.text = knowledgeData.knowledgeTitle
        knowledgeCaption.text = knowledgeData.knowledgeCaption
        if (knowledgeData.knowledgeQuiz == "yes"){
            knowledgeButton.text = context.getString(R.string.kerjakan_button)
        }else knowledgeButton.text = context.getString(R.string.pelajari_button)

        knowledgeButton.setOnClickListener(){
            val knowledgeDetailIntent = Intent(context.applicationContext,KnowledgeDetailActivity::class.java)
            knowledgeDetailIntent.putExtra("KNOWLEDGE_DATA",knowledgeData)
            context.startActivity(knowledgeDetailIntent)
        }
    }
}
