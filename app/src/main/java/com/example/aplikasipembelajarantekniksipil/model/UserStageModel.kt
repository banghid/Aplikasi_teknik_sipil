package com.example.aplikasipembelajarantekniksipil.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_stage")
data class UserStageModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "knowledge_id")
    var knowledgeId: Int,

    @ColumnInfo(name = "chapter_id")
    var chapterId: Int
):Parcelable