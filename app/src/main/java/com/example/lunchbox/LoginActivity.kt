package com.example.lunchbox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login) // Set the login.xml layout

        // Handle Login Button Click
        val loginButton: Button = findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            // Navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the login activity
        }

        val signupLink: TextView = findViewById(R.id.signup_link)

        // Set click listener for Sign Up link
        signupLink.setOnClickListener {
            // Navigate to SignupActivity
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

    }
}
