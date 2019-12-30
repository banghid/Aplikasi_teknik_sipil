package com.example.aplikasipembelajarantekniksipil.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KnowledgeModel(
    var knowledgeId: Int,

    var chapterId: Int,

    var knowledgeTitle: String,

    var knowledgeCaption: String,

    var knowledgeQuiz: String,

    var knowledgeHTML: String
):Parcelable