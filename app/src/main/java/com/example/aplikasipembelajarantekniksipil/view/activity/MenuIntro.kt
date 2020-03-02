package com.example.aplikasipembelajarantekniksipil.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.example.aplikasipembelajarantekniksipil.R
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment

class MenuIntro : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(AppIntroFragment.newInstance("Menu","Menu navigasi untuk melihat halaman utama, rapor, info aplikasi, info pengguna dan logout.",
            R.drawable.manu_image,Color.DKGRAY))
        addSlide(AppIntroFragment.newInstance("Materi","Pilih salah satu materi dengan cara 'KLIK' pada list yang tertera.",
            R.drawable.materi_image,Color.DKGRAY))
        addSlide(AppIntroFragment.newInstance("Rapor","Menampilkan hasil belajar berupa progress belajar dan nilai quiz.",
            R.drawable.rapor_image,Color.DKGRAY))
        addSlide(AppIntroFragment.newInstance("Sub BAB Mater","Menampilkan sub Bab berupa materi dan quiz.",
            R.drawable.flow_image,Color.DKGRAY))

        showSkipButton(true)
    }

    override fun onSkipPressed() {
        super.onSkipPressed()
        val navdrawIntent = Intent(this, NavdrawActivity::class.java)
        startActivity(navdrawIntent)
        finish()
    }

    override fun onDonePressed() {
        super.onDonePressed()
        val navdrawIntent = Intent(this, NavdrawActivity::class.java)
        startActivity(navdrawIntent)
        finish()

    }


}
