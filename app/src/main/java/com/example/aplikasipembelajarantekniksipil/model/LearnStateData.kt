package com.example.aplikasipembelajarantekniksipil.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LearnStateData(
    var knowledgeId: Int,
    var score: String
):Parcelable