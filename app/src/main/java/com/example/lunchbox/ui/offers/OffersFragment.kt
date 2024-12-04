package com.example.lunchbox.ui.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lunchbox.databinding.FragmentOffersBinding
import androidx.appcompat.app.AppCompatActivity
import com.example.lunchbox.R
import com.example.lunchbox.databinding.ActivityMainBinding


class OffersFragment : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val completedButton = findViewById<Button>(R.id.completedButton)
        val sentButton = findViewById<Button>(R.id.sentButton)
        val receivedButton = findViewById<Button>(R.id.receivedButton)

    }
}


//class OffersFragment : Fragment() { }
/*
    private var _binding: FragmentOffersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val offersViewModel =
            ViewModelProvider(this)[OffersViewModel::class.java]

        _binding = FragmentOffersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textOffers
        offersViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root

        val completedButton = findViewById<Button>(R.id.completedButton)
        val sentButton = findViewById<Button>(R.id.sentButton)
        val receivedButton = findViewById<Button>(R.id.receivedButton)


        binding.completedButton.setOnClickListener {

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}*/