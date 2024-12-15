package com.example.lunchbox

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lunchbox.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import java.lang.reflect.Array.get
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.lunchbox.ui.home.HomeFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the user is already logged in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is signed in, show the main content
            setContentView(R.layout.activity_main)
            showHomeFragment()  // Or whatever fragment you want to display first in main activity
        } else {
            // User is not signed in, show the login screen
            setContentView(R.layout.activity_main)  // Or just leave the layout as it is
            showLoginFragment()
        }
    }

    private fun showHomeFragment() {
        // Replace this with logic to display your main activity content
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, HomeFragment())
            .commit()
    }

    private fun showLoginFragment() {
        // Display the login fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, LoginFragment())
            .commit()
    }
}
/*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Set up the custom Toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable the Up button (back arrow) on the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up BottomNavigationView and NavController
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Check if the user is already logged in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // If the user is logged in, navigate to home (or main fragment)
            navController.navigate(R.id.navigation_home)  // Navigate to home screen
        } else {
            // If not logged in, show login screen
            navController.navigate(R.id.loginFragment)  // Navigate to login fragment
        }

        // AppBarConfiguration for top-level destinations
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_profile,  // Include your main screen destinations
                R.id.navigation_add,
            )
        )

        // Set up the action bar with the NavController
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up BottomNavigationView with NavController
        navView.setupWithNavController(navController)
    }

    // Inflate the top navigation menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_navigation, menu)
        return true
    }

    // Handle item selection in the toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                // Navigate to the Profile fragment
                findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_profile)
                true
            }
            R.id.action_offers -> {
                findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.offersFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Handle the up button behavior (back arrow)
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return if (navController.currentDestination?.id == R.id.navigation_home) {
            finish() // Close the activity if you're at the home page
            true
        } else {
            navController.navigateUp() || super.onSupportNavigateUp()
        }
    }
}

*/
/*
class MainActivity : AppCompatActivity() {

    //public var sessionId = "000000001"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the custom Toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable the Up button (back arrow) on the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up BottomNavigationView and NavController
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // AppBarConfiguration for top-level destinations
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                //This makes it so that there is a back arrow to take it to home page
                R.id.navigation_home,
                //R.id.navigation_dashboard,
                //R.id.navigation_add,
            )
        )

        // Set up the action bar with the NavController
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up BottomNavigationView with NavController
        navView.setupWithNavController(navController)
    }

    // Inflate the top navigation menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_navigation, menu)
        return true
    }

    // Handle item selection in the toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                // Navigate to the Profile fragment
                findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_profile)
                true
            }
            R.id.action_offers ->  {
                findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.offersFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Handle the up button behavior (back arrow)
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return if (navController.currentDestination?.id == R.id.navigation_home) {
            // Handle the back button if you're at the home page
            finish() // Close the activity
            true
        } else {
            // Use the NavController to navigate back
            navController.navigateUp() || super.onSupportNavigateUp()
        }
    }
}
*/