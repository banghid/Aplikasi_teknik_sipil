package com.example.aplikasipembelajarantekniksipil.presenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.aplikasipembelajarantekniksipil.database.DatabaseAccess
import com.example.aplikasipembelajarantekniksipil.model.ChapterModel
import com.example.aplikasipembelajarantekniksipil.view.view_interface.ChapterView

class ChapterPresenter(private var view: ChapterView){


    fun setChapter(databaseAccess: DatabaseAccess){
        databaseAccess.openDatabase()
        val cursor = databaseAccess.getChapters()
        val chapters: ArrayList<ChapterModel> = ArrayList()
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            val imageStream:ByteArray = cursor.getBlob(cursor.getColumnIndex("chapter_image"))
            val imageBitmap: Bitmap = BitmapFactory.decodeByteArray(imageStream,0,imageStream.size)
            val iconSteam: ByteArray = cursor.getBlob(cursor.getColumnIndex("chapter_icon"))
            val iconBitmap: Bitmap = BitmapFactory.decodeByteArray(iconSteam,0,iconSteam.size)
            val data = ChapterModel(
                cursor.getInt(cursor.getColumnIndex("chapter_id")),
                imageBitmap,
                cursor.getString(cursor.getColumnIndex("chapter_title")),
                cursor.getString(cursor.getColumnIndex("chapter_caption")),
                cursor.getString(cursor.getColumnIndex("chapter_description")),
                iconBitmap
            )
            chapters.add(data)
            cursor.moveToNext()
        }
        cursor.close()
        view.showChapter(chapters)

        databaseAccess.closeDatabase()

    }
}