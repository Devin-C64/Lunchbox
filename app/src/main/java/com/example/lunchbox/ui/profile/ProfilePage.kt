package com.example.lunchbox.ui.profile


import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lunchbox.R
import com.example.lunchbox.databinding.FragmentProfilePageBinding


class ProfilePage :Fragment() {

    var postedItem = 0

    private lateinit var binding: FragmentProfilePageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePageBinding.inflate(inflater, container, false)

        binding.imageButton2.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_profile_to_settingsFragment)
        }

        // get data from AddFragment
        if (savedInstanceState != null) {
            postedItem = savedInstanceState.getInt("postedItem")
        } else {
            val args = this.arguments
            val inputData = args?.getInt("postedItem")
            val inputName = args?.getString("itemName")
            val inputQuant = args?.getString("itemQuant")
            val inputExpiry = args?.getString("itemExpiry")
            val inputTags = args?.getString("itemTags")
            val image = args?.getByteArray("image")
            //val inputDesc = args?.getString("itemDesc")
            if (inputData != null) {

                val numPosts: TextView = binding.editTextText2
                numPosts.text = "Listings: 1"

                postedItem = inputData
                val ItemNameText: TextView = binding.ItemName
                ItemNameText.text = inputName.toString()
                val ItemQuantText: TextView = binding.ItemQuant
                ItemQuantText.text = inputQuant.toString()
                val ItemExpiryText: TextView = binding.ItemExpiry
                ItemExpiryText.text = inputExpiry
                val ItemTagsText: TextView = binding.TagsText
                ItemTagsText.text = inputTags.toString()

                val ItemImage: ImageView = binding.imageView
                val bmp = image?.let { BitmapFactory.decodeByteArray(image, 0, it.size) }
                ItemImage.setImageBitmap(bmp)

            }
        }

        if (postedItem == 1) {
            binding.newPost.visibility = VISIBLE
            binding.editTextText5.visibility = GONE
        } else {
            binding.newPost.visibility = GONE
            binding.editTextText5.visibility = VISIBLE
        }



        return binding.root
    }
}

/*
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lunchbox.R
import com.example.lunchbox.databinding.FragmentProfilePageBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import com.bumptech.glide.Glide


// Step 1: Define your data classes at the top of this file

data class User(
    val username: String,
    val email: String,
    val password: String,
    val listings: List<Listing>,
    val offers: Offers
)

data class Listing(
    val name: String,
    val quantity: Double,
    val date: String,
    val description: String,
    val tags: List<String>,
    val images: List<String>
)

data class Offers(
    val completed: List<Offer>,
    val sent: List<Offer>,
    val received: List<Offer>
)

data class Offer(
    val name: String,
    val image: String,
    val distance: String
)

class ListingAdapter(context: Context, private val listings: List<Listing>) : ArrayAdapter<Listing>(context, 0, listings) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_listing_layout, parent, false)

        val listing = listings[position]

        val listingName: TextView = view.findViewById(R.id.listingName)
        val listingQuantity: TextView = view.findViewById(R.id.listingQuantity)
        val listingTags: TextView = view.findViewById(R.id.listingTags)
        val listingDate: TextView = view.findViewById(R.id.listingDate)
        val listingDescription: TextView = view.findViewById(R.id.listingDescription)
        val listingImage: ImageView = view.findViewById(R.id.listingImage)

        // Set the values for each view
        listingName.text = listing.name
        listingQuantity.text = "Quantity: ${listing.quantity}"
        listingTags.text = "Tags: ${listing.tags.joinToString()}"
        listingDate.text = "Date: ${listing.date}"
        listingDescription.text = "Description: ${listing.description}"

        // Load the first image (if any) into the ImageView
        /*if (listing.images.isNotEmpty()) {
            Glide.with(context).load(listing.images[0]).into(listingImage) // Glide for loading images
        }
        */

        // Helper function to load image in ImageView
         fun loadImage(context: Context, imageUrlOrName: String, imageView: ImageView) {
            if (imageUrlOrName.startsWith("http")) {
                // If it's a URL, load it with Glide
                Glide.with(context)  // Use the passed context
                    .load(imageUrlOrName)
                    .into(imageView)
            } else {
                // If it's a local resource in drawable
                val resourceId = context.resources.getIdentifier(imageUrlOrName, "drawable", context.packageName)
                if (resourceId != 0) {
                    imageView.setImageResource(resourceId)
                }
            }
        }

// Inside your Fragment (use this function where needed)
        val imageUrlOrName = listing.images[0]
        //loadImage(requireContext(), imageUrlOrName, listingImage)

        return view
    }

}


class ProfilePage : Fragment() {
    // Private variables
    private lateinit var binding: FragmentProfilePageBinding
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var displayList: MutableList<String>

    // Flag to toggle between listings and offers
    private var showingListings = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePageBinding.inflate(inflater, container, false)

        // Set up the button to navigate to the settings fragment
        binding.imageButton2.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_profile_to_settingsFragment)
        }

        // Load data from the JSON file
        loadDataFromJson()

        return binding.root
    }
    private fun loadDataFromJson() {
        try {
            val inputStream = requireContext().assets.open("users.json")
            val reader = InputStreamReader(inputStream)
            val userListType = object : TypeToken<List<User>>() {}.type
            val userDataList = Gson().fromJson<List<User>>(reader, userListType)

            val allListings = mutableListOf<Listing>()
            userDataList.forEach { userData ->
                allListings.addAll(userData.listings)
            }

            // Use a custom adapter
            val adapter = ListingAdapter(requireContext(), allListings)
            binding.listView.adapter = adapter

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error loading data: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
*/
    /*
        private fun loadDataFromJson() {
            try {
                val inputStream = requireContext().assets.open("users.json")
                val reader = InputStreamReader(inputStream)

                // Change to List<User>() if your JSON starts with an array
                val userListType = object : TypeToken<List<User>>() {}.type
                val userDataList = Gson().fromJson<List<User>>(reader, userListType)

                // Iterate through the user data list
                userDataList.forEach { userData ->
                    val listings = userData.listings

                    // Populate the display list with listing names or any other details you want
                    displayList = listings.map { it.name }.distinct().toMutableList()

                    // You can also iterate through the listings to access specific properties like tags or images
                    listings.forEach { listing ->
                        println("Listing Name: ${listing.name}")
                        println("Tags: ${listing.tags.joinToString()}")
                        println("Images: ${listing.images.joinToString()}")
                    }
                }

                // Set up the ArrayAdapter to display the listings in the ListView
                adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, displayList)
                binding.listView.adapter = adapter // Assuming you have a ListView with id listView

            } catch (e: Exception) {
                // Handle error if JSON is not loaded correctly
                e.printStackTrace()  // Print the stack trace for better debugging
                Toast.makeText(requireContext(), "Error loading data: ${e.message}", Toast.LENGTH_SHORT).show()
                displayList = mutableListOf()
            }
        }*/



/*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lunchbox.R
import com.example.lunchbox.databinding.FragmentProfilePageBinding
import java.io.InputStreamReader
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Step 1: Define your data classes at the top of this file

data class User(
    val username: String,
    val email: String,
    val password: String,
    val listings: List<Listing>,
    val offers: Offers
)

data class Listing(
    val name: String,
    val quantity: Double,
    val date: String,
    val description: String,
    val tags: List<String>,
    val images: List<String>
)

data class Offers(
    val completed: List<Offer>,
    val sent: List<Offer>,
    val received: List<Offer>
)

data class Offer(
    val name: String,
    val image: String,
    val distance: String
)

class ProfilePage :Fragment() {
    //public var sessionId = "000000001"

    private lateinit var binding: FragmentProfilePageBinding

    private lateinit var adapter: ArrayAdapter<String>

    private lateinit var displayList: MutableList<String>
    private var showingListings = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePageBinding.inflate(inflater, container, false)

        binding.imageButton2.setOnClickListener{
            it.findNavController().navigate(R.id.action_navigation_profile_to_settingsFragment)
        }
        loadDataFromJson()


        return binding.root
    }

    private fun loadDataFromJson() {
        try {
            val inputStream = requireContext().assets.open("users.json")
            val reader = InputStreamReader(inputStream)
            val userType = object : TypeToken<User>() {}.type // Correct type for User object
            val userData = Gson().fromJson<User>(reader, userType)

            // Access the listings from the loaded data
            val listings = userData.listings

            // Populate the display list with listing names or any other details you want
            displayList = listings.map { it.name }.distinct().toMutableList()

            // You can also iterate through the listings to access specific properties like tags or images
            listings.forEach { listing ->
                println("Listing Name: ${listing.name}")
                println("Tags: ${listing.tags.joinToString()}")
                println("Images: ${listing.images.joinToString()}")
            }

            // Setup the ArrayAdapter or RecyclerView Adapter here
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, displayList)
            binding.listView.adapter = adapter // Assuming you have a ListView with id listView

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error loading data", Toast.LENGTH_SHORT).show()
            displayList = mutableListOf()
        }
    }

}

*/