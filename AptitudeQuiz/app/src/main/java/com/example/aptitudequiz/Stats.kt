package com.example.aptitudequiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson


class Stats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        supportActionBar?.hide();


        var text = findViewById<TextView>(R.id.john)
        text.text = getUser().name

        val allStat: ArrayList<StatFeed> = ArrayList();
        allStat.add(StatFeed("Total Score", R.drawable.icon_15_trophy))
        allStat.add(StatFeed("Total Test", R.drawable.group_25))
        allStat.add(StatFeed("Previous Score", R.drawable.iconfinder_12))
        allStat.add(StatFeed("Time Taken", R.drawable.group_24))


        val simpleGrid: GridView = findViewById(R.id.simpleGridView)
        simpleGrid.adapter = StatAdapter(this, allStat)
        val nxtBtn: ImageButton = findViewById<ImageButton>(R.id.next_btn)
        nxtBtn.setOnClickListener {


            val intent = Intent(this, catogory::class.java);
            startActivity(intent)

        }
        var grid: GridView = findViewById(R.id.simpleGridView)
        grid.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->

            val clickedID = id.toInt()
            Toast.makeText(this, allStat[clickedID].name, Toast.LENGTH_SHORT).show()



           if (allStat[clickedID].name == "Total Test") {
              Toast.makeText(this, "helow", Toast.LENGTH_SHORT).show()
               var db: testdbHelper = testdbHelper(this, null)
               var cursor = db.getName()
               cursor!!.moveToFirst()

               var count = 0
               var marks = 0
                while (cursor.moveToNext()) {

                    if( cursor.getInt(cursor.getColumnIndexOrThrow(testdbHelper.col_uid)) == getUser().id) {

                        var db_marks =
                            cursor.getString(cursor.getColumnIndexOrThrow(testdbHelper.col_marks))
                        marks += db_marks.toInt()
                        count++
                    }
                }
                var intent = Intent(this, statElements::class.java)
                var total_marks = (marks / (count * 10)) * 100
                intent.extras?.putString("marks", total_marks.toString())
                Toast.makeText(this, "Leaving with " + total_marks, Toast.LENGTH_SHORT).show()

                startActivity(intent)


            }
//            if (allStat[clickedID].name == "Total Test") {
//
//
//            }
//            if (allStat[clickedID].name == "Previous Score") {
//
//            }
//            if (allStat[clickedID].name == "Time Taken") {
//
//            }

        })


//        grid.setOnItemClickListener(OnItemClickListener { parent, v, position, id ->
//            val clickedID = id.toInt()
//            var db:testdbHelper = testdbHelper(this,null)
//            var cursor = db.getName(getUser().id)
//            cursor!!.moveToFirst()
//
//            if(allStat[clickedID].name == "Total Score" )
//            {
//                Toast.makeText(this,"helow",Toast.LENGTH_SHORT).show()
//                var count  = 0
//                var marks = 0
//               while(cursor.moveToNext()){
//
//                   var db_marks = cursor.getString(cursor.getColumnIndexOrThrow(testdbHelper.col_marks))
//                   marks+= db_marks.toInt()
//                   count ++
//               }
//
//                var intent = Intent(this,statElements::class.java)
//                var total_marks = (marks/(count*10))*100
//                intent.extras?.putString("marks",total_marks.toString())
//                Toast.makeText(this,"Leaving with "+ total_marks,Toast.LENGTH_SHORT).show()
//
//                startActivity(intent)
//
//
//            }
//            if(allStat[clickedID].name == "Total Test"){
//
//
//            }
//            if(allStat[clickedID].name == "Previous Score")
//            {
//
//            }
//            if(allStat[clickedID].name == "Time Taken")
//            {
//
//            }
//
//
//        })
//
    }

    fun getUser(): users {

        val sharedPrefFile = "kotlinsharedpreference"
        val sharedPreferences: SharedPreferences = getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)




        val gson = Gson()
        val json: String? = sharedPreferences.getString("current_user", "")
        val obj: users = gson.fromJson(json, users::class.java)
        return obj
    }



}