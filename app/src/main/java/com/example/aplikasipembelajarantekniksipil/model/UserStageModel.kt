package com.example.aplikasipembelajarantekniksipil.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
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