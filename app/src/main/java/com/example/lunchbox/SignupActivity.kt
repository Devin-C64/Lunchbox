package com.example.lunchbox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var createAccountButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup) // Ensure this points to the correct layout file

        createAccountButton = findViewById(R.id.create_account_button)

        // Set click listener on the "Create Account" button
        createAccountButton.setOnClickListener {
            // Navigate back to LoginActivity
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finish SignupActivity to remove it from the back stack
        }
    }
}
