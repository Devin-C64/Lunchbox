package com.example.lunchbox.ui.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lunchbox.R
import com.example.lunchbox.databinding.FragmentOffersSentBinding


class OffersSent : Fragment() {

    private lateinit var binding: FragmentOffersSentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOffersSentBinding.inflate(inflater, container, false)


        binding.receivedButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_sentFragment_to_receivedFragment)
        }

        binding.completedButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_sentFragment_to_offersFragment)
        }

        return binding.root
    }

}
