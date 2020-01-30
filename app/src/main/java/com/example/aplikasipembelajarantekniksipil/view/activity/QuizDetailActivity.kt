package com.example.aplikasipembelajarantekniksipil.view.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.database.DatabaseAccess
import com.example.aplikasipembelajarantekniksipil.model.OptionModel
import com.example.aplikasipembelajarantekniksipil.model.QuizModel
import com.example.aplikasipembelajarantekniksipil.presenter.QuizPresenter
import com.example.aplikasipembelajarantekniksipil.view.view_interface.QuizView
import kotlinx.android.synthetic.main.activity_quiz_detail.*
import java.io.InputStream


class QuizDetailActivity : AppCompatActivity(),QuizView {

    private var optionList: ArrayList<OptionModel> = arrayListOf()
    private lateinit var quizPresenter: QuizPresenter
    private lateinit var selectedOption:OptionModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_detail)

        val quizList: ArrayList<QuizModel> = intent?.extras!!.getParcelableArrayList("QUIZ_DATA")!!
        var currentIndex = intent?.extras!!.getInt("QUIZ_INDEX")
        var currentPoint = intent?.extras!!.getInt("CURRENT_POINT")
        quizPresenter = QuizPresenter(this)
        val databaseAccess: DatabaseAccess = DatabaseAccess.getInstance(this)

        if (currentIndex < quizList.size-1){
            when(quizList[currentIndex].quizType){
                "multiple" -> {
                    try {
                        ln_essay.visibility = View.GONE
                        ln_fill_answer.visibility = View.GONE
                        question_tv.text = quizList[currentIndex].quizQuestion
                        if (quizList[currentIndex].quizImagePath != "no image"){
                        quiz_image.setImageBitmap(getBitmapFromAsset(quizList[currentIndex].quizImagePath))
                        quiz_image.visibility = View.VISIBLE
                        }else quiz_image.visibility = View.GONE
                        quizPresenter.setOption(databaseAccess,quizList[currentIndex].quizId)
                        answer_textview1.text = optionList[0].optionDescription
                        answer_textview2.text = optionList[1].optionDescription
                        answer_textview3.text = optionList[2].optionDescription
                        ln_answer.visibility = View.VISIBLE
                    }catch (e: Exception){
                        Log.d(">>>>>QuizDetail","Failed get option '"+e.message+"'")
                    }


                    option_card1.setOnClickListener(object: View.OnClickListener {
                        override fun onClick(v: View?) {
                            option_frame1.background = resources.getDrawable(R.drawable.button_card_selected)
                            option_frame2.background = resources.getDrawable(R.drawable.button_card)
                            option_frame3.background = resources.getDrawable(R.drawable.button_card)

                            selectedOption = optionList[0]
                        }
                    })

                    option_card2.setOnClickListener(object: View.OnClickListener {
                        override fun onClick(v: View?) {
                            option_frame1.background = resources.getDrawable(R.drawable.button_card)
                            option_frame2.background = resources.getDrawable(R.drawable.button_card_selected)
                            option_frame3.background = resources.getDrawable(R.drawable.button_card)

                            selectedOption = optionList[1]
                        }
                    })

                    option_card3.setOnClickListener(object: View.OnClickListener {
                        override fun onClick(v: View?) {
                            option_frame1.background = resources.getDrawable(R.drawable.button_card)
                            option_frame2.background = resources.getDrawable(R.drawable.button_card)
                            option_frame3.background = resources.getDrawable(R.drawable.button_card_selected)

                            selectedOption = optionList[2]
                        }
                    })

                    next_quiz_button.setOnClickListener(object :View.OnClickListener {
                        override fun onClick(v: View?) {
                            currentPoint += selectedOption.optionPoint
                            quizPresenter.lastAnswerUpdate(databaseAccess,quizList[currentIndex].quizId,selectedOption.optionDescription)
                            currentIndex ++
                            val quizDetailIntent = Intent(applicationContext,QuizDetailActivity::class.java)
                            quizDetailIntent.putParcelableArrayListExtra("QUIZ_DATA",quizList)
                            quizDetailIntent.putExtra("QUIZ_INDEX",currentIndex)
                            quizDetailIntent.putExtra("CURRENT_POINT",currentPoint)
                            startActivity(quizDetailIntent)
                            finish()
                        }

                    })
                }
                "essay" -> {
                    ln_fill_answer.visibility = View.GONE
                    ln_answer.visibility = View.GONE
                    ln_essay.visibility = View.VISIBLE
                    question_tv.text = quizList[currentIndex].quizQuestion
                    if (quizList[currentIndex].quizImagePath != "no image"){
                        quiz_image.setImageBitmap(getBitmapFromAsset(quizList[currentIndex].quizImagePath))
                        quiz_image.visibility = View.VISIBLE
                    }else quiz_image.visibility = View.GONE
                    quizPresenter.setOption(databaseAccess,quizList[currentIndex].quizId)

                    next_quiz_button.setOnClickListener(object :View.OnClickListener {
                        override fun onClick(v: View?) {
                            if (optionList[0].optionDescription == essay_edit_text.text.toString()){
                                currentPoint += optionList[0].optionPoint
                            }
                            quizPresenter.lastAnswerUpdate(databaseAccess,quizList[currentIndex].quizId,essay_edit_text.text.toString())
                            currentIndex++
                            val quizDetailIntent = Intent(applicationContext,QuizDetailActivity::class.java)
                            quizDetailIntent.putParcelableArrayListExtra("QUIZ_DATA",quizList)
                            quizDetailIntent.putExtra("QUIZ_INDEX",currentIndex)
                            quizDetailIntent.putExtra("CURRENT_POINT",currentPoint)
                            startActivity(quizDetailIntent)
                            finish()
                        }
                    })
                }
                "fill the blank" -> {
                    ln_fill_answer.visibility = View.VISIBLE
                    ln_answer.visibility = View.GONE
                    ln_essay.visibility = View.GONE
                    question_tv.text = quizList[currentIndex].quizQuestion
                    quiz_image.setImageResource(resources.getIdentifier(quizList[currentIndex].quizImagePath,"drawable",packageName))
                    quiz_image.visibility = View.VISIBLE
                    quizPresenter.setOption(databaseAccess,quizList[currentIndex].quizId)

                    next_quiz_button.setOnClickListener(object: View.OnClickListener {
                        override fun onClick(v: View?) {
                            var i = 0

                            while (i<3){
                                if (fill_et_1.text.toString() == optionList[i].optionDescription){
                                    currentPoint += optionList[i].optionPoint
                                    continue
                                }
                                if (fill_et_2.text.toString() == optionList[i].optionDescription){
                                    currentPoint += optionList[i].optionPoint
                                    continue
                                }
                                if (fill_et_3.text.toString() == optionList[i].optionDescription){
                                    currentPoint += optionList[i].optionPoint
                                    continue
                                }
                            }
                            val lastAnswer = fill_et_1.text.toString() + ", " + fill_et_2.text.toString() + ", " + fill_et_3.text.toString()
                            quizPresenter.lastAnswerUpdate(databaseAccess,quizList[currentIndex].quizId,lastAnswer)
                            currentIndex++
                            val quizDetailIntent = Intent(applicationContext,QuizDetailActivity::class.java)
                            quizDetailIntent.putParcelableArrayListExtra("QUIZ_DATA",quizList)
                            quizDetailIntent.putExtra("QUIZ_INDEX",currentIndex)
                            quizDetailIntent.putExtra("CURRENT_POINT",currentPoint)
                            startActivity(quizDetailIntent)
                            finish()
                        }

                    })
                }
            }
        }else{
            when(quizList[currentIndex].quizType){
                "multiple" -> {
                    ln_essay.visibility = View.GONE
                    ln_fill_answer.visibility = View.GONE
                    question_tv.text = quizList[currentIndex].quizQuestion
                    if (quizList[currentIndex].quizImagePath != "no image"){
                        quiz_image.setImageBitmap(getBitmapFromAsset(quizList[currentIndex].quizImagePath))
                        quiz_image.visibility = View.VISIBLE
                    }else quiz_image.visibility = View.GONE
                    quizPresenter.setOption(databaseAccess,quizList[currentIndex].quizId)
                    answer_textview1.text = optionList[0].optionDescription
                    answer_textview2.text = optionList[1].optionDescription
                    answer_textview3.text = optionList[2].optionDescription
                    ln_answer.visibility = View.VISIBLE

                    option_card1.setOnClickListener(object: View.OnClickListener {
                        override fun onClick(v: View?) {
                            option_frame1.background = resources.getDrawable(R.drawable.button_card_selected)
                            option_frame2.background = resources.getDrawable(R.drawable.button_card)
                            option_frame3.background = resources.getDrawable(R.drawable.button_card)

                            selectedOption = optionList[0]
                        }
                    })

                    option_card2.setOnClickListener(object: View.OnClickListener {
                        override fun onClick(v: View?) {
                            option_frame1.background = resources.getDrawable(R.drawable.button_card)
                            option_frame2.background = resources.getDrawable(R.drawable.button_card_selected)
                            option_frame3.background = resources.getDrawable(R.drawable.button_card)

                            selectedOption = optionList[1]
                        }
                    })

                    option_card3.setOnClickListener(object: View.OnClickListener {
                        override fun onClick(v: View?) {
                            option_frame1.background = resources.getDrawable(R.drawable.button_card)
                            option_frame2.background = resources.getDrawable(R.drawable.button_card)
                            option_frame3.background = resources.getDrawable(R.drawable.button_card_selected)

                            selectedOption = optionList[2]
                        }
                    })

                    next_quiz_button.setOnClickListener(object :View.OnClickListener {
                        override fun onClick(v: View?) {
                            currentPoint += selectedOption.optionPoint
                            quizPresenter.lastAnswerUpdate(databaseAccess,quizList[currentIndex].quizId,selectedOption.optionDescription)
                            quizPresenter.lastPointUpdate(databaseAccess,quizList[currentIndex].knowledgeId,currentPoint)
                            val quizResultIntent = Intent(applicationContext,QuizResultActivity::class.java)
                            quizResultIntent.putExtra("POINT_RESULT", currentPoint)
                            startActivity(quizResultIntent)
                            finish()
                        }

                    })
                }
                "essay" -> {
                    ln_fill_answer.visibility = View.GONE
                    ln_answer.visibility = View.GONE
                    ln_essay.visibility = View.VISIBLE
                    question_tv.text = quizList[currentIndex].quizQuestion
                    if (quizList[currentIndex].quizImagePath != "no image"){
                        quiz_image.setImageBitmap(getBitmapFromAsset(quizList[currentIndex].quizImagePath))
                        quiz_image.visibility = View.VISIBLE
                    }else quiz_image.visibility = View.GONE
                    quizPresenter.setOption(databaseAccess,quizList[currentIndex].quizId)

                    next_quiz_button.setOnClickListener(object :View.OnClickListener {
                        override fun onClick(v: View?) {
                            if (optionList[0].optionDescription == essay_edit_text.text.toString()){
                                currentPoint += optionList[0].optionPoint
                            }
                            quizPresenter.lastAnswerUpdate(databaseAccess,quizList[currentIndex].quizId,essay_edit_text.text.toString())
                            quizPresenter.lastPointUpdate(databaseAccess,quizList[currentIndex].knowledgeId,currentPoint)
                            val quizResultIntent = Intent(applicationContext,QuizResultActivity::class.java)
                            quizResultIntent.putExtra("POINT_RESULT", currentPoint)
                            startActivity(quizResultIntent)
                            finish()
                        }
                    })
                }
                "fill the blank" -> {
                    ln_fill_answer.visibility = View.VISIBLE
                    ln_answer.visibility = View.GONE
                    ln_essay.visibility = View.GONE
                    question_tv.text = quizList[currentIndex].quizQuestion
                    quiz_image.setImageBitmap(getBitmapFromAsset(quizList[currentIndex].quizImagePath))
                    quiz_image.visibility = View.VISIBLE
                    quizPresenter.setOption(databaseAccess,quizList[currentIndex].quizId)

                    next_quiz_button.setOnClickListener(object: View.OnClickListener {
                        override fun onClick(v: View?) {
                            try {
                                var i = 0

                                while (i<3){
                                    if (fill_et_1.text.toString() == optionList[i].optionDescription){
                                        currentPoint += optionList[i].optionPoint
                                        break
                                    }
                                    if (fill_et_2.text.toString() == optionList[i].optionDescription){
                                        currentPoint += optionList[i].optionPoint
                                        break
                                    }
                                    if (fill_et_3.text.toString() == optionList[i].optionDescription){
                                        currentPoint += optionList[i].optionPoint
                                        break
                                    }
                                    Log.d(">>>>>QuizDetail","Success to check")
                                    i++
                                }
                            }catch (e: Exception){
                                Log.d(">>>>>QuizDetail",e.message)
                            }

                            val lastAnswer = fill_et_1.text.toString() + ", " + fill_et_2.text.toString() + ", " + fill_et_3.text.toString()
                            quizPresenter.lastAnswerUpdate(databaseAccess,quizList[currentIndex].quizId,lastAnswer)
                            quizPresenter.lastPointUpdate(databaseAccess,quizList[currentIndex].knowledgeId,currentPoint)
                            val quizResultIntent = Intent(applicationContext,QuizResultActivity::class.java)
                            quizResultIntent.putExtra("POINT_RESULT", currentPoint)
                            startActivity(quizResultIntent)
                            finish()
                        }

                    })
                }
            }
        }
    }

    override fun showQuiz(quizList: List<QuizModel>) {

    }

    override fun showOption(optionList: List<OptionModel>) {
        this.optionList.addAll(optionList)
    }

    fun getBitmapFromAsset(imagePath: String):Bitmap{
        val assetManager = assets

        val iStream: InputStream = assetManager.open(imagePath)

        return BitmapFactory.decodeStream(iStream)

        iStream.close()
    }

}
