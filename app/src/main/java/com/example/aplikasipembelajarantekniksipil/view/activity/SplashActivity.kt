package com.example.aplikasipembelajarantekniksipil.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.adapter.SplashAdapter
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    var mDots: ArrayList<TextView> = arrayListOf()
    lateinit var slidePager: ViewPager
    var currentPage: Int = 0
    private val SPLASH_TIME_OUT_1 = 4000
    private val SPLASH_TIME_OUT_2 = 8000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        slidePager = findViewById(R.id.slidePager)

        val sliderAdapter = SplashAdapter(this)
        slidePager.adapter = sliderAdapter

        addDotsIndicator(0)

        slidePager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                addDotsIndicator(p0)
                currentPage = p0

//                if (p0 == 0) {
//                    prevButton.isEnabled = false
//                    nextButton.isEnabled = true
//                    nextButton.text = "Lanjut"
//                    prevButton.visibility = View.GONE
//                } else {
//                    prevButton.isEnabled = true
//                    prevButton.visibility = View.VISIBLE
//                    nextButton.text = "Selesai"
//                }
            }

        })

//        nextButton.setOnClickListener {
//            if (nextButton.text == "Lanjut") {
//                slidePager.currentItem = currentPage + 1
//            } else {
//                toMainActivity()
//            }
//        }
//
//        prevButton.setOnClickListener { slidePager.currentItem = currentPage - 1 }

        Handler().postDelayed(
            {
                if (currentPage < 1) {
                    slidePager.currentItem = currentPage + 1
                } else toMainActivity()
            }, SPLASH_TIME_OUT_1.toLong()
        )

        Handler().postDelayed(
            { toMainActivity() }, SPLASH_TIME_OUT_2.toLong()
        )
    }

    fun addDotsIndicator(postion: Int) {
        var i = 0
//        dotsLayout.removeAllViews()
        while (i > 2) {
            mDots[i] = TextView(this)
            mDots[i].text = Html.fromHtml("&#8226")
            mDots[i].textSize = 35f
            mDots[i].setTextColor(resources.getColor(R.color.colorTransparentWhite))

//            dotsLayout.addView(mDots[i])
            i++
        }

        if (mDots.size > 0) {
            mDots[postion].setTextColor(resources.getColor(R.color.colorWhite))
        }
    }

    fun toMainActivity() {
        var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null){
            val intentNavdraw = Intent(this,NavdrawActivity::class.java)
            startActivity(intentNavdraw)
            finish()
        }else{
            val intentMainActivity = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intentMainActivity)
            finish()
        }
    }


}
