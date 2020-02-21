package com.example.aplikasipembelajarantekniksipil.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.model.KnowledgeModel
import com.example.aplikasipembelajarantekniksipil.model.LearnStateData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_knowledge_detail.*

class KnowledgeDetailActivity : AppCompatActivity() {

    private lateinit var fDatabase: FirebaseDatabase
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge_detail)

        fDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val databaseReference = fDatabase.getReference("save_state")

        try {
            val knowledgeData: KnowledgeModel = intent.extras!!.getParcelable("KNOWLEDGE_DATA")!!
            wv_knowledge_detail.loadDataWithBaseURL(
                "file:///android_asset/",
                knowledgeData.knowledgeHTML,
                "text/html",
                "UTF-8",
                null
            )

            val stateValue = LearnStateData(
                knowledgeData.knowledgeId,
                "kosong"
                )
            databaseReference.child(currentUser?.uid.toString())
                .child(knowledgeData.chapterId.toString())
                .child(stateValue.knowledgeId.toString())
                .child("score")
                .setValue(stateValue.score)
                .addOnSuccessListener {
                    Toast.makeText(this,"Berhasil menyimpan progress belajar", Toast.LENGTH_SHORT).show()
                }

        }catch (e: Exception){
            Log.d(">>>>>KnowledgeDetail",e.message.toString())
        }

        wv_knowledge_detail.settings.builtInZoomControls = true

    }

}
