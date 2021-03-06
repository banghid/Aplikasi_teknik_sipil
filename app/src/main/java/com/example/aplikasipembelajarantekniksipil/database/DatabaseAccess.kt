package com.example.aplikasipembelajarantekniksipil.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseAccess {
    private var openHelper: SQLiteOpenHelper
    private lateinit var database: SQLiteDatabase

    companion object {
        private lateinit var instance: DatabaseAccess

        fun getInstance(context: Context): DatabaseAccess {
            instance = DatabaseAccess(context)

            return instance
        }
    }

    constructor(context: Context) {
        this.openHelper = DatabaseOpenHelper(context)
    }

    fun openDatabase() {
        this.database = openHelper.writableDatabase
    }

    fun closeDatabase() {
        this.database.close()
    }

    fun getChapters(): Cursor {
        return database.rawQuery("SELECT * FROM chapter_table", null)
    }

    fun getKnowledges(chapterId: Int?): Cursor {
        return database.rawQuery(
            "SELECT * FROM knowledge_table WHERE chapter_id = $chapterId ORDER BY knowledge_number",
            null
        )
    }

    fun getQuizes(knowledgeId: Int?): Cursor {
        return database.rawQuery("SELECT * FROM quiz_table WHERE knowledge_id = $knowledgeId", null)
    }

    fun getOption(quizId: Int?): Cursor {
        return database.rawQuery("SELECT * FROM option_table WHERE quiz_id = $quizId", null)
    }

    fun setLastPoint(knowledgeId: Int?, lastPoint: String) {
        val contentValues = ContentValues()
        contentValues.put("knowledge_html", lastPoint)
        database.update("knowledge_table", contentValues, "knowledge_id = $knowledgeId", null)
    }

    fun setLastAnswer(quizId: Int?, lastAnswer: String) {
        val contentValues = ContentValues()
        contentValues.put("last_answer", lastAnswer)
        database.update("quiz_table", contentValues, "quiz_id = $quizId", null)
    }

    fun getLastPoint(knowledgeId: Int?): Cursor {
        return database.rawQuery(
            "SELECT knowledge_html FROM knowledge_table WHERE knowledge_id = $knowledgeId",
            null
        )
    }

    fun getWhere(table: String, id: Int, key: String): Cursor {
        return database.rawQuery("SELECT * FROM $table WHERE $key = $id", null)
    }

    fun getAllKnowledge():Cursor{
        return database.rawQuery("SELECT * FROM knowledge_table",null)
    }
}