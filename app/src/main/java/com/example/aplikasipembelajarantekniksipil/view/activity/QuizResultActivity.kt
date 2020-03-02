package com.example.aplikasipembelajarantekniksipil.view.activity

import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasipembelajarantekniksipil.R
import kotlinx.android.synthetic.main.activity_quiz_result.*
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal

class QuizResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        val lastPoint = intent?.extras!!.getInt("POINT_RESULT")
        last_score_tv.text = lastPoint.toString()

        if (lastPoint<70){
            message_tv.text = "Maaf anda Belum lulus, jangan patah semangat. Coba lagi."
        }else message_tv.text = "Selamat anda sudah lulus, lanjutkan belajar anda."

        showPrompt()

        done_button.setOnClickListener { finish() }
    }

    private fun showPrompt(){
        val prefManager = PreferenceManager.getDefaultSharedPreferences(this)

        if (!prefManager.getBoolean("QUIZ_RES_TURORIAL", false)) {
            MaterialTapTargetPrompt.Builder(this)
                .setTarget(last_score_tv)
                .setPrimaryText("Hasil Quiz")
                .setSecondaryText("Menampilkan nilai quiz yang baru saja berakhir.")
                .setBackButtonDismissEnabled(true)
                .setFocalColour(Color.DKGRAY)
                .setPromptStateChangeListener { prompt, state ->
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                        state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
                    ) {
                        val prefEditor = prefManager.edit()
                        prefEditor.putBoolean("QUIZ_RES_TURORIAL", true)
                        prefEditor.apply()
                        showDoneQuizPrompt()
                    }
                }
                .setPromptBackground(RectanglePromptBackground())
                .setPromptFocal(RectanglePromptFocal())
                .show()
        }
    }

    private fun showDoneQuizPrompt(){
        MaterialTapTargetPrompt.Builder(this)
            .setTarget(done_button)
            .setPrimaryText("Tombol Selesai")
            .setSecondaryText("Klik tombol 'Selesai' untuk menyelesaikan quiz dan kembali ke menu sub bab.")
            .setBackButtonDismissEnabled(true)
            .setPromptBackground(RectanglePromptBackground())
            .setPromptFocal(RectanglePromptFocal())
            .show()
    }
}
