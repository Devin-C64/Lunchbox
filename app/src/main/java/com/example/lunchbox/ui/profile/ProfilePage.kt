package com.example.lunchbox.ui.profile
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lunchbox.MainActivity
import com.example.lunchbox.R

import com.example.lunchbox.databinding.FragmentProfilePageBinding
import com.example.lunchbox.ui.add.AddFragment

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
        val args = this.arguments
        val inputData = args?.getInt("postedItem")
        if (inputData != null) {
            postedItem = inputData
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
}