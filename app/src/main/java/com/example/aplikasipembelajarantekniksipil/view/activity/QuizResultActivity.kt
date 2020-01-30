package com.example.aplikasipembelajarantekniksipil.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.aplikasipembelajarantekniksipil.R
import kotlinx.android.synthetic.main.activity_quiz_result.*

class QuizResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        val lastPoint = intent?.extras!!.getInt("POINT_RESULT")
        last_score_tv.text = lastPoint.toString()

        if (lastPoint<70){
            message_tv.text = "Maaf anda Belum lulus, jangan patah semangat. Coba lagi."
        }else message_tv.text = "Selamat anda sudah lulus, lanjutkan belajar anda."

        done_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
    }
}
