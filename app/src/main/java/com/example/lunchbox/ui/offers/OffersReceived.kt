package com.example.lunchbox.ui.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lunchbox.R
import com.example.lunchbox.databinding.FragmentOffersReceivedBinding


class OffersReceived : Fragment() {

    private lateinit var binding: FragmentOffersReceivedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOffersReceivedBinding.inflate(inflater, container, false)

        //Function to go to sent page from sent button
        binding.sentButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_receivedFragment_to_sentFragment)
        }
        binding.completedButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_receivedFragment_to_offersFragment)
        }

        return binding.root

    }

    /* to go into mobile nav     <fragment
        android:id="@+id/navigation_offers"
        android:name="com.example.lunchbox.ui.offers.OffersCompleted"
        android:label="Offers"
        tools:layout="@layout/fragment_offers" />*/

}