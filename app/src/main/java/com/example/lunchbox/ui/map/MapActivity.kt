package com.example.lunchbox.ui.map
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.example.lunchbox.R


class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var searchView: SearchView
    private lateinit var infoPanel: RelativeLayout
    private lateinit var personName: TextView
    private lateinit var itemInfo: TextView
    private lateinit var datePosted: TextView
    private val nearbyPeopleMarkers = mutableListOf<Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_activity)

        // Check for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            initializeMap()
        }
        // Initialize the info panel
        infoPanel = findViewById(R.id.infoPanel)
        personName = findViewById(R.id.personName)
        itemInfo = findViewById(R.id.itemInfo)
        datePosted = findViewById(R.id.datePosted)

        // Initialize SearchView for search functionality
        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    handleSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        // Hide info panel initially
        infoPanel.visibility = View.GONE

        // Set up click listener for the info panel
        infoPanel.setOnClickListener {
            val intent = Intent(this@MapActivity, SellerInfoActivity::class.java)

            // Pass the data based on the current marker title
            when (personName.text.toString()) {
                "Posted by: Dan" -> {
                    intent.putExtra("name", "Dan")
                    intent.putExtra("item", "Mozzarella cheese")
                    intent.putExtra("distance", "1.2 miles away")
                    intent.putExtra("datePosted", "15 Nov 2024")
                    intent.putExtra("price", "3.50 $")
                    intent.putExtra("weight", "8 oz")
                    intent.putExtra("expiryDate", "30 Nov 2024")
                }
                "Posted by: Amy" -> {
                    intent.putExtra("name", "Amy")
                    intent.putExtra("item", "Cheddar cheese")
                    intent.putExtra("distance", "0.8 miles away")
                    intent.putExtra("datePosted", "12 Nov 2024")
                    intent.putExtra("price", "2.75 $")
                    intent.putExtra("weight", "5 oz")
                    intent.putExtra("expiryDate", "25 Nov 2024")
                }
                "Posted by: Andy" -> {
                    intent.putExtra("name", "Andy")
                    intent.putExtra("item", "Gouda cheese")
                    intent.putExtra("distance", "1.5 miles away")
                    intent.putExtra("datePosted", "10 Nov 2024")
                    intent.putExtra("price", "4.00 $")
                    intent.putExtra("weight", "6 oz")
                    intent.putExtra("expiryDate", "20 Nov 2024")
                }
            }

            // Start SellerInfoActivity
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializeMap()
        }
    }

    private fun initializeMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Set default location to UDEL, Newark, DE
        val udelLocation = LatLng(39.6780, -75.7520)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(udelLocation, 15f))

        // Add marker click listener
        mMap.setOnMarkerClickListener { marker ->
            showInfoPanel(marker)
            true // Return true to consume the click event
        }
    }

    private fun handleSearch(query: String) {
        // Clear previous markers
        for (marker in nearbyPeopleMarkers) {
            marker.remove()
        }
        nearbyPeopleMarkers.clear()
        // Clear info panel
        infoPanel.visibility = View.GONE

        if (query.equals("cheese", ignoreCase = true)) {
            // Add hardcoded locations for "cheese"
            val person1Location = LatLng(39.6775, -75.7510) // Example coordinates
            val person2Location = LatLng(39.6785, -75.7530)
            val person3Location = LatLng(39.6790, -75.7525)

            val marker1 = mMap.addMarker(MarkerOptions().position(person1Location).title("Dan"))
            val marker2 = mMap.addMarker(MarkerOptions().position(person2Location).title("Amy"))
            val marker3 = mMap.addMarker(MarkerOptions().position(person3Location).title("Andy"))

            // Add markers to the list to track them
            if (marker1 != null) nearbyPeopleMarkers.add(marker1)
            if (marker2 != null) nearbyPeopleMarkers.add(marker2)
            if (marker3 != null) nearbyPeopleMarkers.add(marker3)

            // Move the camera to the first person's location
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(person1Location, 15f))
        }
    }

    private fun showInfoPanel(marker: Marker) {
        when (marker.title) {
            "Dan" -> {
                personName.text = "Posted by: Dan"
                itemInfo.text = "Item: Mozzarella cheese • 1.2 miles away"
                datePosted.text = "Date posted: 15 Nov 2024"
            }
            "Amy" -> {
                personName.text = "Posted by: Amy"
                itemInfo.text = "Item: Cheddar cheese • 0.8 miles away"
                datePosted.text = "Date posted: 12 Nov 2024"
            }
            "Andy" -> {
                personName.text = "Posted by: Andy"
                itemInfo.text = "Item: Gouda cheese • 1.5 miles away"
                datePosted.text = "Date posted: 10 Nov 2024"
            }
        }
        infoPanel.visibility = View.VISIBLE
    }

}