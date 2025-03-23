package com.donate.chillinn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.donate.chillinn.app.AppClass

class SettingsActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val button_logout = findViewById<Button>(R.id.button_logout)
        button_logout.setOnClickListener {
            (application as AppClass).token = ""
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val button_profile = findViewById<ImageView>(R.id.button_profile)
        button_profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }


        val button_home = findViewById<ImageView>(R.id.button_home)
        button_home.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}