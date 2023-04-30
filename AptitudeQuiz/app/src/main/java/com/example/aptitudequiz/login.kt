package com.example.aptitudequiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson

class login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val sharedPrefFile = "kotlinsharedpreference"
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()


        var textregister = findViewById<TextView>(R.id.register)
        textregister.setOnClickListener {
            var intent = Intent(this,register::class.java)
            startActivity(intent)

        }


        var btn_login = findViewById<Button>(R.id.btn_login)
        btn_login.setOnClickListener {


            val db =loginDbHelper(this,null)

            val cursor = db.getName()
            var validated = false
            cursor!!.moveToFirst()

            while (cursor.moveToNext()) {
                val db_name = cursor.getString(cursor.getColumnIndexOrThrow(loginDbHelper.col_name))
                val db_password =
                    cursor.getString(cursor.getColumnIndexOrThrow(loginDbHelper.col_password))
                val db_id =
                    cursor.getInt(cursor.getColumnIndexOrThrow(loginDbHelper.col_id))
                var name = findViewById<EditText>(R.id.username)
                var password = findViewById<EditText>(R.id.password)

                if (db_name == name.text.toString() && db_password == password.text.toString()) {
                    var currentUser = users(name = db_name,password = db_password,id = db_id)
                    var gson = Gson()
                    var user_object:String = gson.toJson(currentUser)
                    editor.putString("current_user",user_object)
                    editor.commit();
                    validated = true
                    var intent = Intent(this, MainActivity::class.java)

                    startActivity(intent)




                }


            }
            if(!validated) {
                Toast.makeText(this, "Username or password incorrect", Toast.LENGTH_SHORT).show()
            }

        }

    }
}
