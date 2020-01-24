package com.example.aplikasipembelajarantekniksipil.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.adapter.QuizDashboardAdapter
import com.example.aplikasipembelajarantekniksipil.database.DatabaseAccess
import com.example.aplikasipembelajarantekniksipil.model.KnowledgeModel
import com.example.aplikasipembelajarantekniksipil.model.QuizModel
import com.example.aplikasipembelajarantekniksipil.presenter.QuizPresenter
import com.example.aplikasipembelajarantekniksipil.view.view_interface.QuizView
import kotlinx.android.synthetic.main.activity_quiz_dashboard.*

class QuizDashboardActivity : AppCompatActivity(),QuizView {
    private var quizesData: ArrayList<QuizModel> = arrayListOf()
    private lateinit var quizDashboardAdapter: QuizDashboardAdapter
    private lateinit var quizPresenter: QuizPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_dashboard)

        val knowledgesData: KnowledgeModel = intent?.extras!!.getParcelable("KNOWLEDGE_DATA")
        val databaseAccess: DatabaseAccess = DatabaseAccess.getInstance(this)
        quizDashboardAdapter = QuizDashboardAdapter(this,quizesData)
        quizPresenter = QuizPresenter(this)
        quizPresenter.setQuizes(databaseAccess,knowledgesData.knowledgeId)

        if(knowledgesData.knowledgeHTML == "kosong"){
            last_score_tv.text = "0"
        }else last_score_tv.text = knowledgesData.knowledgeHTML

        rv_dashboard_answer.layoutManager = LinearLayoutManager(this)
        rv_dashboard_answer.setHasFixedSize(true)
        rv_dashboard_answer.adapter = quizDashboardAdapter
    }

    override fun showQuiz(quizList: List<QuizModel>) {
        this.quizesData.addAll(quizList)
        quizDashboardAdapter.notifyDataSetChanged()
    }
}
