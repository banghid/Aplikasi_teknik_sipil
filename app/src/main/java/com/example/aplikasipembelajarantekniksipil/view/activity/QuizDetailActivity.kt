package com.example.aplikasipembelajarantekniksipil.view.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.database.DatabaseAccess
import com.example.aplikasipembelajarantekniksipil.model.OptionModel
import com.example.aplikasipembelajarantekniksipil.model.QuizModel
import com.example.aplikasipembelajarantekniksipil.presenter.QuizPresenter
import com.example.aplikasipembelajarantekniksipil.view.view_interface.QuizView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_quiz_detail.*
import java.io.InputStream


class QuizDetailActivity : AppCompatActivity(), QuizView {

    private lateinit var fDatabase: FirebaseDatabase
    private lateinit var mAuth: FirebaseAuth
    private var optionList: ArrayList<OptionModel> = arrayListOf()
    private lateinit var quizPresenter: QuizPresenter
    private lateinit var selectedOption: OptionModel
    private var hasSelect = false
    private var chapterId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_detail)

        fDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()

        val quizList: ArrayList<QuizModel> = intent?.extras!!.getParcelableArrayList("QUIZ_DATA")!!
        var currentIndex = intent?.extras!!.getInt("QUIZ_INDEX")
        var currentPoint = intent?.extras!!.getInt("CURRENT_POINT")
        quizPresenter = QuizPresenter(this)
        val databaseAccess: DatabaseAccess = DatabaseAccess.getInstance(this)
        chapterId = quizPresenter.getKnowledge(quizList[currentIndex].knowledgeId, databaseAccess)

        if (currentIndex < quizList.size - 1) {

            try {

                question_tv.text = quizList[currentIndex].quizQuestion
                if (quizList[currentIndex].quizImagePath != "no image") {
                    quiz_image.setImageBitmap(getBitmapFromAsset(quizList[currentIndex].quizImagePath))
                    quiz_image.visibility = View.VISIBLE
                } else quiz_image.visibility = View.GONE
                quizPresenter.setOption(databaseAccess, quizList[currentIndex].quizId)
                answer_textview1.text = optionList[0].optionDescription
                answer_textview2.text = optionList[1].optionDescription
                answer_textview3.text = optionList[2].optionDescription
                ln_answer.visibility = View.VISIBLE
            } catch (e: Exception) {
                Log.d(">>>>>QuizDetail", "Failed get option '" + e.message + "'")
            }


            option_card1.setOnClickListener {
                option_frame1.background = resources.getDrawable(R.drawable.button_card_selected)
                option_frame2.background = resources.getDrawable(R.drawable.button_card)
                option_frame3.background = resources.getDrawable(R.drawable.button_card)
                hasSelect = true

                selectedOption = optionList[0]
            }

            option_card2.setOnClickListener {
                option_frame1.background = resources.getDrawable(R.drawable.button_card)
                option_frame2.background = resources.getDrawable(R.drawable.button_card_selected)
                option_frame3.background = resources.getDrawable(R.drawable.button_card)
                hasSelect = true
                selectedOption = optionList[1]
            }

            option_card3.setOnClickListener {
                option_frame1.background = resources.getDrawable(R.drawable.button_card)
                option_frame2.background = resources.getDrawable(R.drawable.button_card)
                option_frame3.background = resources.getDrawable(R.drawable.button_card_selected)
                hasSelect = true
                selectedOption = optionList[2]
            }

            next_quiz_button.setOnClickListener {
                if (hasSelect) {
                    currentPoint += selectedOption.optionPoint
                    quizPresenter.lastAnswerUpdate(
                        databaseAccess,
                        quizList[currentIndex].quizId,
                        selectedOption.optionDescription
                    )
                    currentIndex++
                    val quizDetailIntent =
                        Intent(applicationContext, QuizDetailActivity::class.java)
                    quizDetailIntent.putParcelableArrayListExtra("QUIZ_DATA", quizList)
                    quizDetailIntent.putExtra("QUIZ_INDEX", currentIndex)
                    quizDetailIntent.putExtra("CURRENT_POINT", currentPoint)
                    startActivity(quizDetailIntent)
                    finish()
                } else {
                    Toast.makeText(this@QuizDetailActivity, "Pilih jawaban!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        } else {

            question_tv.text = quizList[currentIndex].quizQuestion
            if (quizList[currentIndex].quizImagePath != "no image") {
                quiz_image.setImageBitmap(getBitmapFromAsset(quizList[currentIndex].quizImagePath))
                quiz_image.visibility = View.VISIBLE
            } else quiz_image.visibility = View.GONE
            quizPresenter.setOption(databaseAccess, quizList[currentIndex].quizId)
            answer_textview1.text = optionList[0].optionDescription
            answer_textview2.text = optionList[1].optionDescription
            answer_textview3.text = optionList[2].optionDescription
            ln_answer.visibility = View.VISIBLE

            option_card1.setOnClickListener {
                option_frame1.background = resources.getDrawable(R.drawable.button_card_selected)
                option_frame2.background = resources.getDrawable(R.drawable.button_card)
                option_frame3.background = resources.getDrawable(R.drawable.button_card)
                hasSelect = true
                selectedOption = optionList[0]
            }

            option_card2.setOnClickListener {
                option_frame1.background = resources.getDrawable(R.drawable.button_card)
                option_frame2.background = resources.getDrawable(R.drawable.button_card_selected)
                option_frame3.background = resources.getDrawable(R.drawable.button_card)
                hasSelect = true
                selectedOption = optionList[1]
            }

            option_card3.setOnClickListener {
                option_frame1.background = resources.getDrawable(R.drawable.button_card)
                option_frame2.background = resources.getDrawable(R.drawable.button_card)
                option_frame3.background = resources.getDrawable(R.drawable.button_card_selected)
                hasSelect = true
                selectedOption = optionList[2]
            }

            next_quiz_button.setOnClickListener {
                if (hasSelect) {
                    currentPoint += selectedOption.optionPoint
                    quizPresenter.lastAnswerUpdate(
                        databaseAccess,
                        quizList[currentIndex].quizId,
                        selectedOption.optionDescription
                    )
                    quizPresenter.lastPointUpdate(
                        databaseAccess,
                        quizList[currentIndex].knowledgeId,
                        currentPoint
                    )
                    setLastToDB(quizList[currentIndex].knowledgeId, currentPoint)
                    val quizResultIntent =
                        Intent(applicationContext, QuizResultActivity::class.java)
                    quizResultIntent.putExtra("POINT_RESULT", currentPoint)
                    startActivity(quizResultIntent)
                    finish()
                } else {
                    Toast.makeText(this@QuizDetailActivity, "Pilih jawaban!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun showQuiz(quizList: List<QuizModel>) {

    }

    override fun showOption(optionList: List<OptionModel>) {
        this.optionList.addAll(optionList)
    }

    private fun getBitmapFromAsset(imagePath: String): Bitmap {
        val assetManager = assets

        val iStream: InputStream = assetManager.open(imagePath)

        return BitmapFactory.decodeStream(iStream)
    }

    private fun setLastToDB(knowledgeId: Int, currentPoint: Int) {
        val databaseReference = fDatabase.reference
        databaseReference.child("save_state")
            .child(mAuth.currentUser?.uid.toString())
            .child(chapterId.toString())
            .child(knowledgeId.toString())
            .child("score")
            .setValue(currentPoint.toString())
            .addOnSuccessListener {
                Toast.makeText(this, "Berhasil menyimpan nilai", Toast.LENGTH_SHORT).show()
            }
    }

}
