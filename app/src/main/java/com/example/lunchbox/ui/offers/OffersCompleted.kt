package com.example.lunchbox.ui.offers
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.lunchbox.R
import com.example.lunchbox.databinding.FragmentOffersCompletedBinding
import com.example.lunchbox.ui.shared.SharedViewModel

class OffersCompleted : Fragment() {

    private lateinit var binding: FragmentOffersCompletedBinding
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOffersCompletedBinding.inflate(inflater, container, false)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)


        sharedViewModel.showCompletedOffer.observe(viewLifecycleOwner) { isVisible ->
            if (isVisible) {
                binding.completedOffer1.visibility = View.VISIBLE
            } else {
                binding.completedOffer1.visibility = View.GONE
            }
        }

        sharedViewModel.showTextOffersCompleted.observe(viewLifecycleOwner) { isVisible ->
            if (isVisible) {
                binding.textOffersCompleted.visibility = View.INVISIBLE
            } else {
                binding.textOffersCompleted.visibility = View.GONE
            }
        }

        binding.sentButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_offers_to_sent)
        }

        binding.receivedButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_offersFragment_to_receivedFragment)
        }

        return binding.root
    }
}

