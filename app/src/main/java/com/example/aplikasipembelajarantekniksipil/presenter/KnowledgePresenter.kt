package com.example.aplikasipembelajarantekniksipil.presenter

import android.util.Log
import com.example.aplikasipembelajarantekniksipil.database.DatabaseAccess
import com.example.aplikasipembelajarantekniksipil.model.KnowledgeModel
import com.example.aplikasipembelajarantekniksipil.view.view_interface.KnowledgeView

class KnowledgePresenter(private var view:KnowledgeView){

    fun setKnowledge(databaseAccess: DatabaseAccess,chapterId:Int?){
        databaseAccess.openDatabase()
        Log.d(">>>>>KnowledgePresenter","success open database")
        val cursor = databaseAccess.getKnowledges(chapterId)
        Log.d(">>>>>KnowledgePresenter","success gathering data from database")
        val knowledgeList: ArrayList<KnowledgeModel> = arrayListOf()
        Log.d(">>>>>KnowledgePresenter","success instance an arraylist knowledge model")
        cursor.moveToFirst()
        Log.d(">>>>>KnowledgePresenter","success moving a cursor to the first index")
        while (!cursor.isAfterLast){
            try {
                val data = KnowledgeModel(
                    cursor.getInt(cursor.getColumnIndex("knowledge_id")),
                    cursor.getInt(cursor.getColumnIndex("chapter_id")),
                    cursor.getString(cursor.getColumnIndex("knowledge_title")),
                    cursor.getString(cursor.getColumnIndex("knowledge_caption")),
                    cursor.getString(cursor.getColumnIndex("knowledge_quiz")),
                    cursor.getString(cursor.getColumnIndex("knowledge_html"))

                )
                knowledgeList.add(data)
                cursor.moveToNext()
            }catch (e: Exception){
                Log.d(">>>>>KnowledgePresenter",e.message)
            }

            Log.d(">>>>>KnowledgePresenter","success transfer data to temp for "+cursor.position.toString()+" index")

        }
        Log.d(">>>>PresenterCursorData",cursor.toString())
        Log.d(">>>>KnowledgeData",knowledgeList.toString())
        cursor.close()
        view.showKnowledge(knowledgeList)

        databaseAccess.closeDatabase()

    }
}