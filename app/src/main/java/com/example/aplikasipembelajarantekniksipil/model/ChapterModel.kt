package com.example.aplikasipembelajarantekniksipil.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChapterModel(
    var chapterId:Int,

    var chapterImage:Bitmap,

    var chapterTitle:String,

    var chapterCaption:String,

    var chapterDescription:String,

    var chapterIcon:Bitmap
):Parcelable