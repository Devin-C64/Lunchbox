package com.example.lunchbox.ui.message
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lunchbox.R
import com.example.lunchbox.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    private var username: String? = null
    private var itemName: String? = null
    private var itemDistance: Double? = null
    private var itemBuilding: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the username passed from SearchFragment
        username = arguments?.getString("username")
        itemName = arguments?.getString("itemName")
        itemDistance = arguments?.getDouble("itemDistance")
        itemBuilding = arguments?.getString("itemBuilding")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set the title or initial message with the user's name
        binding.textViewTitle.text = "Chat with $username"

        // Handle sending messages (for simplicity, this just clears the input)
        binding.buttonSend.setOnClickListener {
            val message = binding.editTextMessage.text.toString()
            if (message.isNotBlank()) {
                // Add your message sending logic here
                val bundle = Bundle().apply {
                    // Passing the same data from SearchFragment + the message
                    putString("username", username)
                    putString("itemName", itemName)
                    putDouble("itemDistance", itemDistance ?: 0.0)
                    putString("itemBuilding", itemBuilding)
                    putString("message", message)  // Pass the new message
                }
                binding.editTextMessage.text.clear()
                findNavController().navigate(R.id.sentFragment, bundle)
            }
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}