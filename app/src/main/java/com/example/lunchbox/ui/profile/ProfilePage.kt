package com.example.lunchbox.ui.profile
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lunchbox.R

import com.example.lunchbox.databinding.FragmentProfilePageBinding

class ProfilePage :Fragment() {

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

        return binding.root
    }
}