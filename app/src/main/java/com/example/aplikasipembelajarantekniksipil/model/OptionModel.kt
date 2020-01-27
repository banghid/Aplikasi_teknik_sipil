package com.example.aplikasipembelajarantekniksipil.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OptionModel(
    var optionId: Int,
    var quizId: Int,
    var optionDescription: String,
    var optionPoint: Int
):Parcelable