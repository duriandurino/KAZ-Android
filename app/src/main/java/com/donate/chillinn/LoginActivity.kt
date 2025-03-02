package com.donate.chillinn

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edittext_username = findViewById<EditText>(R.id.edittext_username)
        val edittext_password = findViewById<EditText>(R.id.edittext_password)

        val logoutUser = intent.getStringExtra("logout_username")
        val logoutFullname = intent.getStringExtra("logout_fullname")

        var fullname = ""

        intent?.let{
            it.getStringExtra("username")?.let{username ->
                edittext_username.setText(username)
            }
            it.getStringExtra("password")?.let{password ->
                edittext_password.setText(password)
            }
            it.getStringExtra("fullname")?.let{fname ->
                fullname = fname
            }
        }

        if(!logoutUser.isNullOrEmpty() || !logoutFullname.isNullOrEmpty()){
            Toast.makeText(this, "${logoutFullname} has logged out (user: ${logoutUser})",
                Toast.LENGTH_LONG).show()
        }

        val button_register = findViewById<Button>(R.id.button_register)
        button_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        val button_login = findViewById<Button>(R.id.button_login)
        button_login.setOnClickListener {
            val username = edittext_username.text
            val password = edittext_password.text

            if(username.isNullOrEmpty() || password.isNullOrEmpty()){
                Toast.makeText(this, "Username and Password should not be empty",
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else{
                if(username.toString() == "admin" && password.toString() == "123"){
                    Toast.makeText(this, "Logged in with ${username.toString()}",
                        Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, HomeActivity::class.java).apply{
                        putExtra("username", edittext_username.text.toString())
                        putExtra("password", edittext_password.text.toString())
                        putExtra("fullname", fullname)
                    })
                }else{
                    Toast.makeText(this, "Wrong username or password",
                        Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            }
        }

    }
}