package com.donate.chillinn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val textview_welcomeuser = findViewById<TextView>(R.id.textview_welcomeuser)
        var username = ""
        var fullname = ""

        intent?.let{
            it.getStringExtra("fullname")?.let{fname ->
                fullname = fname
                textview_welcomeuser.setText("Welcome ${fname}!")
            }

            it.getStringExtra("username")?.let{user ->
                username = user
            }
        }

        val tempbutton_back = findViewById<Button>(R.id.tempbutton_back)
        tempbutton_back.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java).apply{
                putExtra("logout_username", username)
                putExtra("logout_fullname", fullname)
            })
        }
    }
}