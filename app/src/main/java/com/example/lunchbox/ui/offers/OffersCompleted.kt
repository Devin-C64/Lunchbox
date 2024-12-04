package com.example.lunchbox.ui.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lunchbox.R
import com.example.lunchbox.databinding.FragmentOffersCompletedBinding

class OffersCompleted : Fragment() {

    private lateinit var binding: FragmentOffersCompletedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOffersCompletedBinding.inflate(inflater, container, false)

        //Function to go to sent page from sent button
        binding.sentButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_offers_to_sent)
        }
        binding.receivedButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_offersFragment_to_receivedFragment)
        }
        binding.homeButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_offersFragment_to_navigation_home)
        }


        return binding.root
    }
}