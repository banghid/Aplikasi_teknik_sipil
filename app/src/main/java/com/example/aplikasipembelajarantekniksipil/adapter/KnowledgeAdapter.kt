package com.example.aplikasipembelajarantekniksipil.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.model.KnowledgeModel
import com.example.aplikasipembelajarantekniksipil.view.activity.KnowledgeDetailActivity
import com.example.aplikasipembelajarantekniksipil.view.activity.QuizDashboardActivity
import com.github.vipulasri.timelineview.TimelineView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.knowledge_item.view.*


class KnowledgeAdapter(private var context: Context, private var knowledgeList: ArrayList<KnowledgeModel>):
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
        val userAuth = FirebaseAuth.getInstance()
        p0.bindKnowledge(context,knowledgeList[p1], userAuth, p1)
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
    private var knowledgeViewNumber = view.knowledge_number

    init {
        knowledgeTimeline.initLine(viewType)
    }

    fun bindKnowledge(context: Context, knowledgeData: KnowledgeModel, userAuth: FirebaseAuth, knowledgeNumber:Int){
        knowledgeTitle.text = knowledgeData.knowledgeTitle
        knowledgeCaption.text = knowledgeData.knowledgeCaption


//        val dbReference = FirebaseDatabase.getInstance().reference


        if (knowledgeData.knowledgeQuiz == "yes"){
            knowledgeButton.text = context.getString(R.string.kerjakan_button)
            knowledgeViewNumber.visibility = View.GONE
            knowledgeButton.setOnClickListener{
                val quizDashboardIntent = Intent(context.applicationContext,QuizDashboardActivity::class.java)
                quizDashboardIntent.putExtra("KNOWLEDGE_DATA",knowledgeData)
                context.startActivity(quizDashboardIntent)
            }
        }else {
            knowledgeButton.text = context.getString(R.string.pelajari_button)
            val numberText = ((knowledgeNumber + 2) / 2).toString() + "."
            knowledgeViewNumber.text =  numberText
            knowledgeButton.setOnClickListener{
                val knowledgeDetailIntent = Intent(context.applicationContext,KnowledgeDetailActivity::class.java)
                knowledgeDetailIntent.putExtra("KNOWLEDGE_DATA",knowledgeData)
                context.startActivity(knowledgeDetailIntent)
            }
        }

//        knowledgeButton.background = context.getDrawable(R.drawable.runded_button)


//        dbReference.child("save_state")
//            .child(userAuth.currentUser?.uid.toString())
//            .child(knowledgeData.chapterId.toString())
//            .child(knowledgeData.knowledgeId.toString())
//            .addValueEventListener(object : ValueEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onDataChange(p0: DataSnapshot) {
//                    val resultKey = p0.key
//                    if (knowledgeData.knowledgeId.toString() == resultKey){
//                        knowledgeButton.background = context.getDrawable(R.drawable.rounded_clear_button)
//                    }
//                }
//
//            })


    }
}
