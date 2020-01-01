package com.example.aplikasipembelajarantekniksipil.view.view_interface

import com.example.aplikasipembelajarantekniksipil.model.KnowledgeModel
import com.example.aplikasipembelajarantekniksipil.model.UserStageModel

interface  KnowledgeView{
    fun showKnowledge(knowledges: List<KnowledgeModel>)
    fun loadUserStage(userStages: List<UserStageModel>)
}