package com.donate.chillinn

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONObject
import java.io.IOException
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class LoginActivity : Activity() {
    private val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edittext_email = findViewById<EditText>(R.id.edittext_email)
        val edittext_password = findViewById<EditText>(R.id.edittext_password)


        intent?.let{
            it.getStringExtra("email")?.let{email ->
                edittext_email.setText(email)
            }
            it.getStringExtra("password")?.let{password ->
                edittext_password.setText(password)
            }
        }

        val button_register = findViewById<Button>(R.id.button_register)
        button_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        val button_login = findViewById<Button>(R.id.button_login)
        button_login.setOnClickListener {
            val email:String = edittext_email.text.toString()
            val password:String = edittext_password.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Email and Password should not be empty",
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        val json = JSONObject().apply {
            put("email", email)
            put("password", password)
        }

        val mediaType = "application/json".toMediaType()
        val requestBody = json.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url("https://eminent-chalk-lotus.glitch.me/users/login")
            .addHeader("X-API-Key", "kizkazkuz2025")
            .post(requestBody)
            .build()

        println("Request URL: ${request.url}") // Log the URL
        println("Request Headers: ${request.headers}") // Log the headers
        println("Request Body: ${json.toString()}") // Log the request body

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Network error: ${e.message}", Toast.LENGTH_LONG).show()
                }
                println("Network error: ${e.message}") // Log network errors
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                println("Response Code: ${response.code}") // Log the response code
                println("Response Body: $responseBody") // Log the response body

                if (response.isSuccessful && responseBody != null) {
                    try {
                        val jsonResponse = JSONObject(responseBody)
                        val token = jsonResponse.getString("token")
                        val user = jsonResponse.getJSONObject("user")
                        val userEmail = user.getString("email")

                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            intent.putExtra("token", token)
                            startActivity(intent)
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, "Error parsing response: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                        println("Error parsing response: ${e.message}") // Log parsing errors
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Login failed: ${responseBody}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}