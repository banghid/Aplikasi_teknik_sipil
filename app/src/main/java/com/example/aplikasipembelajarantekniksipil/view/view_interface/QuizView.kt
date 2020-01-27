package com.example.aplikasipembelajarantekniksipil.view.view_interface

import com.example.aplikasipembelajarantekniksipil.model.OptionModel
import com.example.aplikasipembelajarantekniksipil.model.QuizModel

interface QuizView{
    fun showQuiz(quizList: List<QuizModel>)
    fun showOption(optionList: List<OptionModel>)
}