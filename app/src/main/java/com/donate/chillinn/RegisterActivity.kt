package com.donate.chillinn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edittext_username = findViewById<EditText>(R.id.edittext_username)
        val edittext_password = findViewById<EditText>(R.id.edittext_password)
        val edittext_fullname = findViewById<EditText>(R.id.edittext_fullname)
        val edittext_confirmpassword = findViewById<EditText>(R.id.edittext_confirmpassword)

        val button_submit = findViewById<Button>(R.id.button_submit)
        button_submit.setOnClickListener {
            if (edittext_username.text.toString().isNullOrEmpty()
                || edittext_password.text.toString().isNullOrEmpty()
                || edittext_fullname.text.toString().isNullOrEmpty()
            ) {

                Toast.makeText(this, "Form is not filled completely",
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(edittext_password.toString() == edittext_confirmpassword.toString()){
                startActivity(
                    Intent(this, LoginActivity::class.java).apply{
                        putExtra("username", edittext_username.text.toString())
                        putExtra("password", edittext_password.text.toString())
                        putExtra("fullname", edittext_fullname.text.toString())
                    }
                )
            }
        }
    }
}