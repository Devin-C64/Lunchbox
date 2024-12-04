package com.example.lunchbox.ui.map
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.SearchView
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

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var searchView: SearchView
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
        try {
            mMap = googleMap

            // Set default location to UDEL, Newark, DE
            val udelLocation = LatLng(39.6780, -75.7520)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(udelLocation, 15f))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun handleSearch(query: String) {
        // Clear previous markers
        for (marker in nearbyPeopleMarkers) {
            marker.remove()
        }
        nearbyPeopleMarkers.clear()

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

}