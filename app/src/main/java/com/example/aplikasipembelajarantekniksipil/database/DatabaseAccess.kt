package com.example.aplikasipembelajarantekniksipil.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseAccess{
    private var openHelper: SQLiteOpenHelper
    private lateinit var database: SQLiteDatabase

    companion object{
        private lateinit var instance:DatabaseAccess

        fun getInstance(context: Context):DatabaseAccess{
            instance = DatabaseAccess(context)

            return instance
        }
    }

    constructor(context: Context){
        this.openHelper = DatabaseOpenHelper(context)
    }

    fun openDatabase(){
        this.database = openHelper.writableDatabase
    }

    fun closeDatabase(){
        this.database.close()
    }

    fun getChapters():Cursor{
        return database.rawQuery("SELECT * FROM chapter_table", null)
    }

    fun getKnowledges(chapterId:Int):Cursor{
        return database.rawQuery("SELECT * FROM knowledge_table WHERE chapter_id = $chapterId",null)
    }

}