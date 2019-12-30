package com.example.aplikasipembelajarantekniksipil.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.model.KnowledgeModel
import kotlinx.android.synthetic.main.activity_knowledge_detail.*

class KnowledgeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge_detail)



        try {
            val knowledgeData: KnowledgeModel = intent.extras!!.getParcelable("KNOWLEDGE_DATA")!!
            wv_knowledge_detail.loadDataWithBaseURL(
                "file:///android_assets/",
                knowledgeData.knowledgeHTML,
                "text/html",
                "UTF-8",
                null
            )
        }catch (e: Exception){
            Log.d(">>>>>KnowledgeDetail",e.message)
        }

    }
}
