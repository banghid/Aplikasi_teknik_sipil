package com.example.aplikasipembelajarantekniksipil.view.view_interface

import com.example.aplikasipembelajarantekniksipil.model.KnowledgeModel

interface  KnowledgeView{
    fun showKnowledge(knowledges: List<KnowledgeModel>)
}