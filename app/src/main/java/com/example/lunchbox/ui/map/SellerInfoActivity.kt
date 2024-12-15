package com.example.lunchbox.ui.map

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lunchbox.R
class SellerInfoActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_info_activity)

        val name = intent.getStringExtra("name")
        val item = intent.getStringExtra("item")
        val distance = intent.getStringExtra("distance")
        val datePosted = intent.getStringExtra("datePosted")
        val price = intent.getStringExtra("price")
        val weight = intent.getStringExtra("weight")
        val expiryDate = intent.getStringExtra("expiryDate")

        findViewById<TextView>(R.id.postedBy).text = "Posted by: $name"
        findViewById<TextView>(R.id.itemDetails).text = "Item: $item • $distance"
        findViewById<TextView>(R.id.datePosted).text = "Date posted: $datePosted"
        findViewById<TextView>(R.id.priceDetails).text = "Price: $price"
        findViewById<TextView>(R.id.weightDetails).text = "Weight: $weight"
        findViewById<TextView>(R.id.expiryDetails).text = "Expiry Date: $expiryDate"
    }
}