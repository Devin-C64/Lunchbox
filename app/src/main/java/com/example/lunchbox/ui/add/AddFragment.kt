package com.example.lunchbox.ui.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lunchbox.R
import com.example.lunchbox.databinding.FragmentAddBinding
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import android.graphics.BitmapFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.OutputStream
import java.lang.Exception
import java.util.*

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

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        // Toast for Post Item
        binding.postItemBtn.setOnClickListener {
            val itemNameText: EditText = requireView().findViewById(R.id.item_name_edit)
            val itemQuantText: EditText = requireView().findViewById(R.id.quantity_edit)
            val itemExpiryDate: DatePicker = requireView().findViewById(R.id.date_picker_container)
            val itemTagsText: EditText = requireView().findViewById(R.id.item_tags_edit)
            val itemDescText: EditText = requireView().findViewById(R.id.item_description_edit)
            val image: ImageView = binding.previewView

            val itemName = itemNameText.text.toString()
            val itemQuant = itemQuantText.text.toString()
            val itemExpiry = "${itemExpiryDate.month + 1}/${itemExpiryDate.dayOfMonth}/${itemExpiryDate.year}"
            val itemTags = itemTagsText.text.toString().split(",").map { it.trim() }  // Convert to a list of tags
            val itemDesc = itemDescText.text.toString()

            if (itemName.isNotEmpty() && itemQuant.isNotEmpty() && itemExpiry.isNotEmpty() && itemTags.isNotEmpty() && itemDesc.isNotEmpty() && image.drawable != null) {
                val imageBundle = image.drawToBitmap()
                val stream = ByteArrayOutputStream()
                imageBundle.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                Toast.makeText(requireContext(), "Item Posted", Toast.LENGTH_SHORT).show()

                // Generate the image filename or URL (for simplicity, assuming it's a static file name here)
                val imageFileName = "new_item_image.png"  // Use a unique name for the image if required

                // Create a Listing object
                val newListing = Listing(
                    name = itemName,
                    quantity = itemQuant.toDouble(),
                    date = itemExpiry,
                    description = itemDesc,
                    tags = itemTags,
                    images = listOf(imageFileName)  // Add the image to the list
                )

                // Load existing users data from JSON
                val users = loadDataFromJson()

                // Find the current user (assuming "cisc482test" as the username)
                val currentUserIndex = users.indexOfFirst { it.username == "cisc482test" }
                if (currentUserIndex != -1) {
                    val user = users[currentUserIndex]
                    val updatedListings = user.listings.toMutableList()
                    updatedListings.add(newListing)

                    // Create a new user object with updated listings
                    val updatedUser = user.copy(listings = updatedListings)

                    // Update the users list with the modified user
                    users[currentUserIndex] = updatedUser

                    // Save the updated users list back to the file
                    saveDataToJson(users)
                    Toast.makeText(requireContext(), "file saved", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                }

                // Bundle data to send to Profile fragment
                val bundle = Bundle()
                bundle.putInt("postedItem", 1)
                bundle.putString("itemName", itemName)
                bundle.putString("itemQuant", itemQuant)
                bundle.putString("itemExpiry", itemExpiry)
                bundle.putString("itemTags", itemTags.joinToString(", "))
                bundle.putByteArray("image", byteArray)
                bundle.putString("itemDesc", itemDesc)

                it.findNavController().navigate(R.id.navigation_profile, bundle)  // Navigating to 'Profile' page
            } else {
                // Handle empty fields with appropriate toasts
                if (itemName.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please provide an item name",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (itemQuant.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please provide an item quantity",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (itemExpiry.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please provide an expiry date",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (itemDesc.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please provide a description",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (itemTags.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please provide some tags",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (image.drawable == null) {
                    Toast.makeText(
                        requireContext(),
                        "Please provide an image",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Camera Functionality
        val cameraButton = binding.captureImgBtn
        val albumButton = binding.fromAlbumBtn
        val previewView = binding.previewView
        var isCamera = 0

        if (previewView.drawable != null) {
            previewView.visibility = View.VISIBLE
        } else {
            previewView.visibility = View.GONE
        }

        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                previewView.visibility = View.VISIBLE
                if (isCamera == 1) {
                    val image = result.data?.extras?.get("data") as Bitmap
                    previewView.setImageBitmap(image)
                } else {
                    val imageURI = result.data?.data as Uri
                    previewView.setImageURI(imageURI)
                }
            }
        }

        cameraButton.setOnClickListener {
            isCamera = 1
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcher.launch(cameraIntent)
        }

        albumButton.setOnClickListener {
            isCamera = 0
            val albumIntent = Intent(MediaStore.ACTION_PICK_IMAGES)
            launcher.launch(albumIntent)
        }

        return binding.root
    }

    // Method to load data from users.json
    private fun loadDataFromJson(): MutableList<User> {
        try {
            val inputStream = requireContext().assets.open("users.json")
            val reader = InputStreamReader(inputStream)
            val userListType = object : TypeToken<List<User>>() {}.type
            return Gson().fromJson(reader, userListType) // Deserialize and return the list
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error loading data: ${e.message}", Toast.LENGTH_SHORT).show()
            return mutableListOf() // Return an empty list if an error occurs
        }
    }

    // Method to save updated data back to users.json
    private fun saveDataToJson(users: MutableList<User>) {
        try {
            // Convert users list to JSON
            val json = Gson().toJson(users)

            // Write the updated data back to the file
            val outputStream: OutputStream = requireContext().openFileOutput("users.json", Context.MODE_PRIVATE)
            outputStream.write(json.toByteArray())
            outputStream.close()

            Toast.makeText(requireContext(), "Data saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error saving data: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}

/*
class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding

    @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        // Toast for Post Item
        binding.postItemBtn.setOnClickListener {
            val itemNameText : EditText = requireView().findViewById(R.id.item_name_edit)
            val itemQuantText : EditText = requireView().findViewById(R.id.quantity_edit)
            val itemExpiryDate : DatePicker = requireView().findViewById(R.id.date_picker_container)
            val itemTagsText : EditText = requireView().findViewById(R.id.item_tags_edit)
            val itemDescText : EditText = requireView().findViewById(R.id.item_description_edit)
            val image: ImageView = binding.previewView

            val itemName = itemNameText.text.toString()
            val itemQuant = itemQuantText.text.toString()
            val itemExpiry = (itemExpiryDate.month+1).toString() + "/" + itemExpiryDate.dayOfMonth.toString() + "/" + itemExpiryDate.year.toString()
            val itemTags = itemTagsText.text.toString()
            val itemDesc = itemDescText.text.toString()

            if (itemName.isNotEmpty() and itemQuant.isNotEmpty() and itemExpiry.isNotEmpty() and itemTags.isNotEmpty() and itemDesc.isNotEmpty() and (image.drawable!=null)) {
                val imageBundle = image.drawToBitmap()
                val stream = ByteArrayOutputStream()
                imageBundle.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                Toast.makeText(requireContext(), "Item Posted", Toast.LENGTH_SHORT).show()

                // Bundle data to send to Profile fragment
                val bundle = Bundle()
                bundle.putInt("postedItem", 1)
                bundle.putString("itemName", itemName)
                bundle.putString("itemQuant", itemQuant)
                bundle.putString("itemExpiry", itemExpiry)
                bundle.putString("itemTags", itemTags)
                bundle.putByteArray("image", byteArray)
                bundle.putString("itemDesc", itemDesc)


                it.findNavController().navigate(R.id.navigation_profile, bundle)  // Navigating to 'Profile' page
            } else{
                if (itemName.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please provide an item name",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (itemQuant.isEmpty()){
                    Toast.makeText(
                        requireContext(),
                        "Please provide an item quantity",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (itemExpiry.isEmpty()){
                    Toast.makeText(
                        requireContext(),
                        "Please provide an expiry date",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (itemDesc.isEmpty()){
                    Toast.makeText(
                        requireContext(),
                        "Please provide a description",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (itemTags.isEmpty()){
                    Toast.makeText(
                        requireContext(),
                        "Please provide some tags",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (image.drawable==null){
                    Toast.makeText(
                        requireContext(),
                        "Please provide an image",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Camera Functionality
        val cameraButton = binding.captureImgBtn
        val albumButton = binding.fromAlbumBtn
        val previewView = binding.previewView
        var isCamera = 0

        if (previewView.drawable != null){
            previewView.visibility = VISIBLE
        } else {
            previewView.visibility = GONE
        }

        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                previewView.visibility = VISIBLE
                if (isCamera == 1){
                    val image = result.data?.extras?.get("data") as Bitmap
                    previewView.setImageBitmap(image)
                } else {
                    val imageURI = result.data?.data as Uri
                    previewView.setImageURI(imageURI)
                }
            }
        }

        cameraButton.setOnClickListener {
            isCamera = 1
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcher.launch(cameraIntent)
        }

        albumButton.setOnClickListener {
            isCamera = 0
            val albumIntent = Intent(MediaStore.ACTION_PICK_IMAGES)
            launcher.launch((albumIntent))
        }

        return binding.root
    }

}
*/

/*

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        val addViewModel =
            ViewModelProvider(this).get(AddViewModel::class.java)

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.itemNameEdit
        addViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Toast for Save Draft
        binding.saveDraftbtn.setOnClickListener {
            Toast.makeText(requireContext(), "Draft Saved", Toast.LENGTH_SHORT).show()
        }

        // Toast for Post Item
        binding.postItemBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Item Posted", Toast.LENGTH_SHORT).show()
            val newFrag = HomeFragment()
            val fragManager = requireActivity().supportFragmentManager
            val transaction = fragManager.beginTransaction()
            transaction.replace(R.id.navigation_add, newFrag).commit()
        }

        // Camera Functionality
        val cameraButton = binding.captureImgBtn
        val previewView = binding.previewView

        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val image = result.data?.extras?.get("data") as Bitmap
                previewView.setImageBitmap(image)
            }
        }

        cameraButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcher.launch(cameraIntent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

*/