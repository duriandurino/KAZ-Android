package com.donate.chillinn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

class HomeActivity : Activity() {
    private val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val textview_email = findViewById<TextView>(R.id.textview_email)
        val textview_firstname = findViewById<TextView>(R.id.textview_firstname)
        val textview_middlename = findViewById<TextView>(R.id.textview_middlename)
        val textview_lastname = findViewById<TextView>(R.id.textview_lastname)
        val textview_phonenum = findViewById<TextView>(R.id.textview_phonenum)
        val textview_role = findViewById<TextView>(R.id.textview_role)
        val textview_created = findViewById<TextView>(R.id.textview_created)

        var userToken = ""

        intent?.let{
            it.getStringExtra("token")?.let{token ->
                userToken = token
            }
        }

        fetchUserInfo(userToken, textview_email, textview_firstname, textview_middlename, textview_lastname, textview_phonenum, textview_role, textview_created)

        val button_logout = findViewById<Button>(R.id.button_logout)
        button_logout.setOnClickListener {
            userToken = ""
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fetchUserInfo(token: String, emailView: TextView, firstnameView: TextView, middlenameView: TextView, lastnameView: TextView, phonenumView: TextView, roleView: TextView, createdView: TextView) {
        val request = Request.Builder()
            .url("https://eminent-chalk-lotus.glitch.me/users/userinfobyemail")
            .addHeader("X-API-Key", "kizkazkuz2025")
            .addHeader("Authorization", "Bearer $token")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@HomeActivity, "Network error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val jsonResponse = JSONObject(responseBody)
                    val email = jsonResponse.getString("email")
                    val firstname = jsonResponse.getString("firstname")
                    val middlename = jsonResponse.getString("middlename")
                    val lastname = jsonResponse.getString("lastname")
                    val phonenum = jsonResponse.getString("phone_num")
                    val role = jsonResponse.getString("role")
                    val created = jsonResponse.getString("created")

                    runOnUiThread {
                        emailView.text = email
                        firstnameView.text = firstname
                        middlenameView.text = middlename
                        lastnameView.text = lastname
                        phonenumView.text = phonenum
                        roleView.text = role
                        createdView.text = created
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@HomeActivity, "Failed to fetch user info: ${responseBody}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}