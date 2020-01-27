package com.example.aplikasipembelajarantekniksipil.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.model.OptionModel
import kotlinx.android.synthetic.main.question_option_item.view.*

class OptionAdapter(private var context: Context,private var optionList: ArrayList<OptionModel>): RecyclerView.Adapter<OptionHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OptionHolder {
        return OptionHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.question_option_item,
                p0,
                false
            ),
            p1
        )
    }

    override fun getItemCount(): Int = optionList.size

    override fun onBindViewHolder(p0: OptionHolder, p1: Int) {
        return p0.bindAnswer(context,optionList[p1])
    }

}

class OptionHolder(view: View, viewType:Int):RecyclerView.ViewHolder(view){
    private var frameButton = view.option_frame
    private var optionCard = view.option_card
    private var optionTv = view.answer_textview
    private var buttonSelected = false

    fun bindAnswer(context: Context,optionData: OptionModel){
        optionTv.text = optionData.optionDescription

        optionCard.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if (!buttonSelected){
                    frameButton.background = context.resources.getDrawable(R.drawable.button_card_selected)
                    buttonSelected = true
                }else {
                    frameButton.background = context.resources.getDrawable(R.drawable.button_card)
                    buttonSelected = false
                }
            }
        })
    }
}