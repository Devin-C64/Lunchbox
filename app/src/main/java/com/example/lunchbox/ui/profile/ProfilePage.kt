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

        binding.imageButton2.setOnClickListener{
            it.findNavController().navigate(R.id.action_navigation_profile_to_settingsFragment)
        }

        // get data from AddFragment
        if (savedInstanceState != null){
            postedItem = savedInstanceState.getInt("postedItem")
        } else {
            val args = this.arguments
            val inputData = args?.getInt("postedItem")
            val inputName = args?.getString("itemName")
            val inputQuant = args?.getString("itemQuant")
            val inputExpiry = args?.getString("itemExpiry")
            val inputTags = args?.getString("itemTags")
            val image = args?.getByteArray("image")
            if (inputData != null) {
                postedItem = inputData
                val ItemNameText : TextView = binding.ItemName
                ItemNameText.text = inputName.toString()
                val ItemQuantText : TextView = binding.ItemQuant
                ItemQuantText.text = inputQuant.toString()
                val ItemExpiryText : TextView = binding.ItemExpiry
                ItemExpiryText.text = inputExpiry
                val ItemTagsText : TextView = binding.TagsText
                ItemTagsText.text = inputTags.toString()

                val ItemImage : ImageView = binding.imageView
                val bmp = image?.let { BitmapFactory.decodeByteArray(image, 0, it.size) }
                ItemImage.setImageBitmap(bmp)

            }
        }

        if (postedItem == 1){
            binding.newPost.visibility = VISIBLE
            binding.editTextText5.visibility = GONE
        } else{
            binding.newPost.visibility = GONE
            binding.editTextText5.visibility = VISIBLE
        }

        return binding.root
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putInt("postedItem",postedItem)
//    }
//
//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        if (savedInstanceState != null) {
//            postedItem = savedInstanceState.getInt("postedItem")
//        }
//    }
}