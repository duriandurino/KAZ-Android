package com.donate.chillinn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.donate.chillinn.app.AppClass
import org.json.JSONObject
import java.io.IOException
import okhttp3.*

class ProfileActivity : Activity (){
    private val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val textview_email = findViewById<TextView>(R.id.textview_email)
        val textview_firstname = findViewById<TextView>(R.id.textview_firstname)
        val textview_lastname = findViewById<TextView>(R.id.textview_lastname)
        val textview_phonenum = findViewById<TextView>(R.id.textview_phonenum)
        val textview_role = findViewById<TextView>(R.id.textview_role)
        val textview_created = findViewById<TextView>(R.id.textview_created)

        var userToken = (application as AppClass).token

        fetchUserInfo(userToken, textview_email, textview_firstname, textview_lastname, textview_phonenum, textview_role, textview_created)

        val button_profile = findViewById<ImageView>(R.id.button_profile)
        button_profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        val button_settings = findViewById<ImageView>(R.id.button_settings)
        button_settings.setOnClickListener {
            (application as AppClass).token = ""
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val button_home = findViewById<ImageView>(R.id.button_home)
        button_home.setOnClickListener {
            (application as AppClass).token = ""
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fetchUserInfo(token: String, emailView: TextView, firstnameView: TextView, lastnameView: TextView, phonenumView: TextView, roleView: TextView, createdView: TextView) {
        val request = Request.Builder()
            .url("https://eminent-chalk-lotus.glitch.me/users/userinfobyemail")
            .addHeader("X-API-Key", "kizkazkuz2025")
            .addHeader("Authorization", "Bearer $token")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@ProfileActivity, "Network error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val jsonResponse = JSONObject(responseBody)
                    val email = jsonResponse.getString("email")
                    val firstname = jsonResponse.getString("firstname")
                    val lastname = jsonResponse.getString("lastname")
                    val phonenum = jsonResponse.getString("phone_num")
                    val role = jsonResponse.getString("role")
                    val created = jsonResponse.getString("created")

                    runOnUiThread {
                        emailView.text = email
                        firstnameView.text = firstname
                        lastnameView.text = lastname
                        phonenumView.text = phonenum
                        roleView.text = role
                        createdView.text = created
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@ProfileActivity, "Failed to fetch user info: ${responseBody}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}