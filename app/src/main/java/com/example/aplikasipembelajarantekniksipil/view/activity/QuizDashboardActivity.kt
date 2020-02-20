package com.example.aplikasipembelajarantekniksipil.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.adapter.QuizDashboardAdapter
import com.example.aplikasipembelajarantekniksipil.database.DatabaseAccess
import com.example.aplikasipembelajarantekniksipil.model.KnowledgeModel
import com.example.aplikasipembelajarantekniksipil.model.OptionModel
import com.example.aplikasipembelajarantekniksipil.model.QuizModel
import com.example.aplikasipembelajarantekniksipil.presenter.QuizPresenter
import com.example.aplikasipembelajarantekniksipil.view.view_interface.QuizView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_quiz_dashboard.*


class QuizDashboardActivity : AppCompatActivity(), QuizView {

    private lateinit var dbReference: DatabaseReference
    private lateinit var userAuth: FirebaseAuth
    private var quizesData: ArrayList<QuizModel> = arrayListOf()
    private lateinit var quizDashboardAdapter: QuizDashboardAdapter
    private lateinit var quizPresenter: QuizPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_dashboard)

        dbReference = FirebaseDatabase.getInstance().reference
        userAuth = FirebaseAuth.getInstance()
        val knowledgeData: KnowledgeModel = intent?.extras!!.getParcelable("KNOWLEDGE_DATA")!!
        val databaseAccess: DatabaseAccess = DatabaseAccess.getInstance(this)
        quizDashboardAdapter = QuizDashboardAdapter(this, quizesData)
        quizPresenter = QuizPresenter(this)
        quizPresenter.setQuizes(databaseAccess, knowledgeData.knowledgeId)

        val lastPoint = quizPresenter.getLastPoint(databaseAccess, knowledgeData.knowledgeId)

        try {
            dbReference.child("save_state")
                .child(userAuth.currentUser?.uid.toString())
                .child(knowledgeData.knowledgeId.toString())
                .child("score")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        last_score_tv.text = "0"
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        last_score_tv.text = p0.getValue(String::class.java)
                    }

                })
        }catch (e: Exception){
            if (lastPoint == "kosong") {
                last_score_tv.text = "0"
            } else last_score_tv.text = lastPoint
        }

        rv_dashboard_answer.layoutManager =
            LinearLayoutManager(this)
        rv_dashboard_answer.setHasFixedSize(true)
        rv_dashboard_answer.adapter = quizDashboardAdapter



        start_button.setOnClickListener {
            val quizDetailIntent = Intent(applicationContext, QuizDetailActivity::class.java)
            quizDetailIntent.putParcelableArrayListExtra("QUIZ_DATA", quizesData)
            quizDetailIntent.putExtra("QUIZ_INDEX", 0)
            quizDetailIntent.putExtra("CURRENT_POINT", 0)
            startActivity(quizDetailIntent)
            finish()
        }
    }

    override fun showQuiz(quizList: List<QuizModel>) {
        this.quizesData.addAll(quizList)
        quizDashboardAdapter.notifyDataSetChanged()
    }

    override fun showOption(optionList: List<OptionModel>) {

    }


}
