package com.example.aptitudequiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var textregister = findViewById<TextView>(R.id.signUp)
        textregister.setOnClickListener {
            var intent = Intent(this,login::class.java)
            startActivity(intent)

        }
        var btn_login = findViewById<Button>(R.id.btn_signup)
        btn_login.setOnClickListener {


            val db = loginDbHelper(this, null)
            var name = findViewById<EditText>(R.id.usname)
            var password = findViewById<EditText>(R.id.password)
            var email = findViewById<EditText>(R.id.email)

            var result = db.addName(name = name.text.toString(),email = email.text.toString(),password = password.text.toString())
            Toast.makeText(this,"Successfully registered",Toast.LENGTH_SHORT).show()

            var intent = Intent(this,login::class.java)
            startActivity(intent)


        }



    }
}