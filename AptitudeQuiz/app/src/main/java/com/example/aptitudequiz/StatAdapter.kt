package com.example.aptitudequiz

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class StatAdapter(private val context : Context, private  val AllState:ArrayList<StatFeed>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return AllState.count()
    }

    override fun getItem(p0: Int): Any {
        return p0.toLong()
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
       val layoutInflater :LayoutInflater = LayoutInflater.from(context)
        val mainRow :View = layoutInflater.inflate(R.layout.itemlist,p2,false)
        val statText :TextView = mainRow.findViewById<TextView>(R.id.stat_text)
        val statImg:ImageView = mainRow.findViewById<ImageView>(R.id.stat_image)
        statText.text = AllState[p0].name
        statImg.setImageResource(AllState[p0].image)



        return mainRow

    }


}