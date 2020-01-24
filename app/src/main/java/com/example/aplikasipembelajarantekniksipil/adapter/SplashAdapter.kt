package com.example.aplikasipembelajarantekniksipil.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.aplikasipembelajarantekniksipil.R

class SplashAdapter(private val context: Context) : PagerAdapter(){

    lateinit var layoutInflater: LayoutInflater
    var slideImages = arrayOf(
        R.drawable.logo_tutwurihandayani,
        R.drawable.logo_uny
    )

    var slideDescription = arrayOf(
        "Dasinan adalah singkatan dari Dasar Konstruksi dan Bangunan.",
        "Dasinan adalah suatu aplikasi yang membantu pembelajaran pada mata pelajaran Dasar-dasar Konstruksi setingkat kelas X pada sekolah mengengah kejuruan."
    )

    var slideTitle = arrayOf(
        "SELAMAT DATANG DI DALAM DASINAN",
        "APA ITU DASINAN?"
    )

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1 as RelativeLayout
    }

    override fun getCount(): Int {
        return slideTitle.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.splash_selamat_datang, container, false)

        val splashImage: ImageView = view.findViewById(R.id.image_splash)
        val splashTitle: TextView = view.findViewById(R.id.textview_splash)
        val splashDescription: TextView = view.findViewById(R.id.desc_splash)

        splashImage.setImageResource(slideImages[position])
        splashTitle.text = slideTitle[position]
        splashDescription.text = slideDescription[position]

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}