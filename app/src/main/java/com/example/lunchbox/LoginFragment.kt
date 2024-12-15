package com.example.lunchbox

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseUser


class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailEditText = view.findViewById(R.id.email_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        loginButton = view.findViewById(R.id.login_btn)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Check if the email and password match the test credentials
            if (email == "cisc482test@gmail.com" && password == "test12345!") {
                // Always log in with these credentials
                val intent = Intent(requireActivity(), MainActivity::class.java)  // Use requireActivity() for the context
                startActivity(intent)
                requireActivity().finish()
            } else {
                // Show error message if credentials are wrong
                Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
/*
    private fun navigateToMainActivity() {
        // Use NavController to navigate to the MainActivity

        val intent =  Intent(LoginFragment::class.java, MainActivity::class.java)
                startActivity(intent)
        /*
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


        // Remove the LoginFragment from the FragmentManager
        requireActivity().supportFragmentManager.beginTransaction().apply {
            remove(this@LoginFragment)
            commit()
        }

        // Close the LoginActivity
        requireActivity().finish()
        */

    }
}


/*
class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views
        emailEditText = binding.findViewById(R.id.email_edit_text)
        passwordEditText = binding.findViewById(R.id.password_edit_text)
        loginButton = binding.findViewById(R.id.login_btn)

        // Set login button click listener
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInWithEmailAndPassword(email, password)
            } else {
                Toast.makeText(requireContext(), "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }

        return binding
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to home or another activity
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(requireContext(), "Login successful! Welcome, ${user?.email}", Toast.LENGTH_SHORT).show()
                    // Navigate to the next screen or activity
                    // For example: findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    // If sign in fails, display a message to the user
                    Toast.makeText(requireContext(), "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
*/