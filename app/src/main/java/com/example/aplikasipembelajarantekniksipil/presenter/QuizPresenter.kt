package com.example.aplikasipembelajarantekniksipil.presenter

import android.util.Log
import com.example.aplikasipembelajarantekniksipil.database.DatabaseAccess
import com.example.aplikasipembelajarantekniksipil.model.QuizModel
import com.example.aplikasipembelajarantekniksipil.view.view_interface.QuizView

class QuizPresenter(private var view: QuizView){

    fun setQuizes(database: DatabaseAccess, knowledgeId: Int?){
        database.openDatabase()
        val cursor = database.getQuizes(knowledgeId)
        val quizList: ArrayList<QuizModel> = arrayListOf()
        cursor.moveToFirst()

        while (!cursor.isAfterLast){
            try {
                val data = QuizModel(
                    cursor.getInt(cursor.getColumnIndex("quiz_id")),
                    cursor.getInt(cursor.getColumnIndex("knowledge_id")),
                    cursor.getString(cursor.getColumnIndex("question")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("last_answer")),
                    cursor.getString(cursor.getColumnIndex("image_path"))

                )
                quizList.add(data)
                cursor.moveToNext()
            }catch (e: Exception){
                Log.d(">>>>>QuizPresenter",e.message)
            }

            Log.d(">>>>>QuizPresenter","success transfer data to temp for "+cursor.position.toString()+" index")

        }
        Log.d(">>>>PresenterCursorData",cursor.toString())
        Log.d(">>>>QuizPresenter",quizList.toString())
        cursor.close()
        view.showQuiz(quizList)

        database.closeDatabase()
    }

}