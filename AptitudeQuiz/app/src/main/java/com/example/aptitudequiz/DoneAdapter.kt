package com.example.apptitudetest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.aptitudequiz.DoneFeed
import com.example.aptitudequiz.R

class DoneAdapter(private val context: Context,
                  private val info: DoneFeed
): BaseAdapter() {
    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): Any {
        return  position.toLong()
    }

    override fun getItemId(position: Int): Long {
        return  position.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater: LayoutInflater= LayoutInflater.from(context);
        val statRow: View = layoutInflater.inflate(R.layout.donelist, viewGroup, false)
        var totalScore:Int = (Integer.parseInt(info.qCorrectAnswers)*10)
        statRow.findViewById<TextView>(R.id.score).text = "Score: ${totalScore}/100"

        statRow.findViewById<TextView>(R.id.q_number).text = "Number of Questions: ${info.qNumbers}"
        statRow.findViewById<TextView>(R.id.correct_answer).text = "Correct Questions: ${info.qCorrectAnswers}"
        statRow.findViewById<TextView>(R.id.attempted_question).text = "Attempted Questions: ${info.qNumbers}"
        statRow.findViewById<TextView>(R.id.negative_marks).text = "Negative marks: ${info.qNegative}"


        return statRow
    }
}
