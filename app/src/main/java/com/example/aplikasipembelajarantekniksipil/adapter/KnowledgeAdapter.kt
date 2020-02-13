package com.example.aplikasipembelajarantekniksipil.adapter

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.model.KnowledgeModel
import com.example.aplikasipembelajarantekniksipil.model.UserStageModel
import com.example.aplikasipembelajarantekniksipil.view.activity.KnowledgeDetailActivity
import com.example.aplikasipembelajarantekniksipil.view.activity.QuizDashboardActivity
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.android.synthetic.main.knowledge_item.view.*


class KnowledgeAdapter(private var context: Context, private var knowledgeList: ArrayList<KnowledgeModel>,private var userStageList: List<UserStageModel>):
    RecyclerView.Adapter<KnowledgeHolder>(){
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
        p0.bindKnowledge(context,knowledgeList[p1],userStageList)
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position,itemCount)
    }

    fun getList():ArrayList<KnowledgeModel>{
        return knowledgeList
    }

}

class KnowledgeHolder(view: View, viewType:Int):
    RecyclerView.ViewHolder(view){

    private var knowledgeTitle = view.knowledge_title
    private var knowledgeCaption = view.knowledge_caption
    private var knowledgeButton = view.knowledge_button
    private var knowledgeTimeline = view.knowledge_timeline
    private var stageDone: Boolean = false

    init {
        knowledgeTimeline.initLine(viewType)
    }

    fun bindKnowledge(context: Context, knowledgeData: KnowledgeModel, userStageList: List<UserStageModel>){
        knowledgeTitle.text = knowledgeData.knowledgeTitle
        knowledgeCaption.text = knowledgeData.knowledgeCaption
        val currentStage = UserStageModel(knowledgeData.knowledgeId, knowledgeData.chapterId)
        var i = 0
        while (i< userStageList.size){
            if (userStageList[i] == currentStage){
                stageDone = true
                break
            }
            i++
        }

        if (knowledgeData.knowledgeQuiz == "yes"){
            knowledgeButton.text = context.getString(R.string.kerjakan_button)
            knowledgeButton.setOnClickListener{
                val quizDashboardIntent = Intent(context.applicationContext,QuizDashboardActivity::class.java)
                quizDashboardIntent.putExtra("KNOWLEDGE_DATA",knowledgeData)
                context.startActivity(quizDashboardIntent)
            }
        }else {
            knowledgeButton.text = context.getString(R.string.pelajari_button)
            knowledgeButton.setOnClickListener{
                val knowledgeDetailIntent = Intent(context.applicationContext,KnowledgeDetailActivity::class.java)
                knowledgeDetailIntent.putExtra("KNOWLEDGE_DATA",knowledgeData)
                context.startActivity(knowledgeDetailIntent)
            }
        }
        if (stageDone){
            knowledgeButton.background = context.getDrawable(R.drawable.rounded_clear_button)
        }else knowledgeButton.background = context.getDrawable(R.drawable.runded_button)


    }
}
