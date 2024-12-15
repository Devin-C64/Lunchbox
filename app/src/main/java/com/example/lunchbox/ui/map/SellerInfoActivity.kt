package com.example.lunchbox.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lunchbox.R

class SellerInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.seller_info_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve arguments passed to the fragment
        val name = arguments?.getString("name")
        val item = arguments?.getString("item")
        val distance = arguments?.getString("distance")
        val datePosted = arguments?.getString("datePosted")
        val price = arguments?.getString("price")
        val weight = arguments?.getString("weight")
        val expiryDate = arguments?.getString("expiryDate")

        // Set data to the views
        view.findViewById<TextView>(R.id.postedBy).text = "Posted by: $name"
        view.findViewById<TextView>(R.id.itemDetails).text = "Item: $item • $distance"
        view.findViewById<TextView>(R.id.datePosted).text = "Date posted: $datePosted"
        view.findViewById<TextView>(R.id.priceDetails).text = "Price: $price"
        view.findViewById<TextView>(R.id.weightDetails).text = "Weight: $weight"
        view.findViewById<TextView>(R.id.expiryDetails).text = "Expiry Date: $expiryDate"
    }

    companion object {
        // Factory method to create a new instance of the fragment with arguments
        fun newInstance(
            name: String,
            item: String,
            distance: String,
            datePosted: String,
            price: String,
            weight: String,
            expiryDate: String
        ): SellerInfoFragment {
            val fragment = SellerInfoFragment()
            val args = Bundle()
            args.putString("name", name)
            args.putString("item", item)
            args.putString("distance", distance)
            args.putString("datePosted", datePosted)
            args.putString("price", price)
            args.putString("weight", weight)
            args.putString("expiryDate", expiryDate)
            fragment.arguments = args
            return fragment
        }
    }
}

/*
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
*/