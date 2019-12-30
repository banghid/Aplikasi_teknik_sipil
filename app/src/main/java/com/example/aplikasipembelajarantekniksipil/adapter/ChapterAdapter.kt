package com.example.aplikasipembelajarantekniksipil.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.model.ChapterModel
import com.example.aplikasipembelajarantekniksipil.view.activity.KnowledgeFlowActivity
import kotlinx.android.synthetic.main.chapter_item.view.*

class ChapterAdapter(private var context: Context,private var chapterList: ArrayList<ChapterModel>): RecyclerView.Adapter<ChapterHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ChapterHolder {
        return ChapterHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.chapter_item,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int = chapterList.size

    override fun onBindViewHolder(p0: ChapterHolder, p1: Int) {
        return p0.bindChapter(context,chapterList[p1])
    }

//    fun getList():ArrayList<ChapterModel>{
//        return chapterList
//    }

}

class ChapterHolder(view: View):RecyclerView.ViewHolder(view){
    private var chapterImage = view.chapter_image
    private var chapterTitle = view.chapter_title
    private var chapterCaption = view.chapter_caption
    private var chapterDescription = view.chapter_description
    private var chapterIcon = view.chapter_icon
    private var chapterItem = view.chapter_item

    fun bindChapter(context: Context, chapterData: ChapterModel){
        chapterImage.setImageBitmap(chapterData.chapterImage)
        chapterTitle.text = chapterData.chapterTitle
        chapterCaption.text = chapterData.chapterCaption
        chapterDescription.text = chapterData.chapterDescription
        chapterIcon.setImageBitmap(chapterData.chapterIcon)

        chapterItem.setOnClickListener{
            val knowledgeFlowIntent = Intent(context.applicationContext,KnowledgeFlowActivity::class.java)
            knowledgeFlowIntent.putExtra("CHAPTER_ID",chapterData.chapterId)

            context.startActivity(knowledgeFlowIntent)
        }
    }
}