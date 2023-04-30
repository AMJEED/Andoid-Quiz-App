package com.example.aptitudequiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast

class catogory : AppCompatActivity() {
    var category: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catogory)

        val startBtn: ImageButton = findViewById(R.id.startBtn);
        val intent: Intent = Intent(this, Questions::class.java);

        startBtn.setOnClickListener {
            intent.putExtra("category",category)
            startActivity(intent);



        }




    }


    fun handleClick(view: View) {

        var widget1: ImageView = findViewById(R.id.computer)
        var widget2: ImageView = findViewById(R.id.math)
        var widget3: ImageView = findViewById(R.id.gk)


        if (view.id == R.id.computer) {

            category = 18

            widget1.setImageResource(R.drawable.aftercomputer)
            widget2.setImageResource(R.drawable.math)
            widget3.setImageResource(R.drawable.general)

        } else if (view.id == R.id.math) {
            category = 19

            widget1.setImageResource(R.drawable.computer)
            widget2.setImageResource(R.drawable.aftermath)
            widget3.setImageResource(R.drawable.general)

        } else if (view.id == R.id.gk) {
            category = 9

            widget1.setImageResource(R.drawable.computer)
            widget2.setImageResource(R.drawable.math)
            widget3.setImageResource(R.drawable.aftergeneral)
        } else
            category = 9


    }


}