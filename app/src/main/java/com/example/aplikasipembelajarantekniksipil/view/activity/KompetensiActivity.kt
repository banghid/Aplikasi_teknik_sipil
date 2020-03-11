package com.example.aplikasipembelajarantekniksipil.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasipembelajarantekniksipil.R
import kotlinx.android.synthetic.main.activity_kompetensi.*

class KompetensiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kompetensi)

        val contentData = intent?.getStringExtra("KOMPETENSI_DASAR")

        if (contentData != null){
            wv_kompetensi.loadDataWithBaseURL(
                "file:///android_asset/",
                contentData,
                "text/html",
                "UTF-8",
                null
            )
        }

        wv_kompetensi.settings.builtInZoomControls = true
    }



}
