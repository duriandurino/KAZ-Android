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
import org.json.JSONObject
import java.io.IOException
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class RegisterActivity : Activity() {
    private val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edittext_email = findViewById<EditText>(R.id.edittext_email)
        val edittext_password = findViewById<EditText>(R.id.edittext_password)
        val edittext_firstname = findViewById<EditText>(R.id.edittext_firstname)
        val edittext_lastname = findViewById<EditText>(R.id.edittext_lastname)
        val edittext_phonenum = findViewById<EditText>(R.id.edittext_phonenum)
        val edittext_confirmpassword = findViewById<EditText>(R.id.edittext_confirmpassword)

        val button_submit = findViewById<Button>(R.id.button_submit)
        button_submit.setOnClickListener {
            val email = edittext_email.text.toString()
            val password = edittext_password.text.toString()
            val firstname = edittext_firstname.text.toString()
            val lastname = edittext_lastname.text.toString()
            val phonenum = edittext_phonenum.text.toString()
            val confirmPassword = edittext_confirmpassword.text.toString()

            if (email.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || phonenum.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            registerUser(email, password, firstname, lastname, phonenum)
        }
    }
    private fun registerUser(email: String, password: String, firstname: String, lastname: String, phonenum: String) {
        val json = JSONObject().apply {
            put("email", email)
            put("password", password)
            put("firstname", firstname)
            put("lastname", lastname)
            put("phone_num", phonenum)
            put("role", "user")
        }

        val mediaType = "application/json".toMediaType()
        val requestBody = json.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url("https://eminent-chalk-lotus.glitch.me/users/add-user")
            .addHeader("X-API-Key", "kizkazkuz2025")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "Network error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val jsonResponse = JSONObject(responseBody)
                    val message = jsonResponse.getString("message")
                    val user = jsonResponse.getJSONObject("user")

                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("password", password)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Registration failed: ${responseBody}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}