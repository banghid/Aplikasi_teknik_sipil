package com.example.aplikasipembelajarantekniksipil.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.model.QuizModel
import kotlinx.android.synthetic.main.dashboard_answer_item.view.*

class QuizDashboardAdapter(private val context: Context, private val quizList: ArrayList<QuizModel>):RecyclerView.Adapter<QuizDashboardHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): QuizDashboardHolder {
        return QuizDashboardHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.dashboard_answer_item,
                p0,
                false
            ),
            p1
        )
    }

    override fun getItemCount(): Int = quizList.size

    override fun onBindViewHolder(p0: QuizDashboardHolder, p1: Int) {
        return p0.bindQuiz(context, quizList[p1],p1)
    }
}

class QuizDashboardHolder(view: View, viewType:Int):RecyclerView.ViewHolder(view){
    private var numberTextview = view.number_textview
    private var answerTextview = view.answer_textview

    fun bindQuiz(context: Context, quizData: QuizModel, quizNumber:Int){
        val number = quizNumber + 1
        numberTextview.text = number.toString()
        answerTextview.text = quizData.quizLastAnswer
    }
}