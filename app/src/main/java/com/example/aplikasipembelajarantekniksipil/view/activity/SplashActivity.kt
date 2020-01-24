package com.example.aplikasipembelajarantekniksipil.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.adapter.SplashAdapter
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    var mDots: ArrayList<TextView> = arrayListOf()
    lateinit var slidePager: ViewPager
    lateinit var dotsLayout: LinearLayout
    var currentPage:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        slidePager = findViewById(R.id.slidePager)
        dotsLayout = findViewById(R.id.dotsLayout)

        val sliderAdapter = SplashAdapter(this)
        slidePager.adapter = sliderAdapter

        addDotsIndicator(0)

        slidePager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                addDotsIndicator(p0)
                currentPage = p0

                if (p0 == 0){
                    prevButton.isEnabled = false
                    nextButton.isEnabled = true
                    nextButton.text = "Lanjut"
                    prevButton.visibility = View.GONE
                }else{
                    prevButton.isEnabled = true
                    prevButton.visibility = View.VISIBLE
                    nextButton.text = "Selesai"
                }
            }

        })

        nextButton.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                if (nextButton.text == "Lanjut"){
                    slidePager.currentItem = currentPage + 1
                }else{
                    val intentMainActivity = Intent(applicationContext,MainActivity::class.java)
                    startActivity(intentMainActivity)
                    finish()
                }
            }
        })

        prevButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                slidePager.currentItem = currentPage - 1
            }

        })
    }

    fun addDotsIndicator(postion:Int){
        var i = 0
        dotsLayout.removeAllViews()
        while (i > 2){
            mDots[i] = TextView(this)
            mDots[i].text = Html.fromHtml("&#8226")
            mDots[i].textSize = 35f
            mDots[i].setTextColor(resources.getColor(R.color.colorTransparentWhite))

            dotsLayout.addView(mDots[i])
            i++
        }

        if (mDots.size > 0){
            mDots[postion].setTextColor(resources.getColor(R.color.colorWhite))
        }
    }


}
