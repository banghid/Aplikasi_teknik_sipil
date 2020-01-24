package com.example.aplikasipembelajarantekniksipil.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuizModel(
    var quizId: Int,
    var knowledgeId: Int,
    var quizQuestion: String,
    var quizType: String,
    var quizLastAnswer: String,
    var quizImagePath: String
):Parcelable