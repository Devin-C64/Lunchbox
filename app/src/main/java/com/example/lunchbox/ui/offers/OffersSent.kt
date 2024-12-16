package com.example.lunchbox.ui.offers

import android.os.Bundle
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lunchbox.R
import com.example.lunchbox.databinding.FragmentOffersSentBinding
import android.widget.ArrayAdapter

class OffersSent : Fragment() {

    private lateinit var binding: FragmentOffersSentBinding

    private var username: String? = null
    private var itemName: String? = null
    private var itemDistance: Double? = null
    private var itemBuilding: String? = null
    private var message: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOffersSentBinding.inflate(inflater, container, false)

        arguments?.let {
            username = it.getString("username")
            itemName = it.getString("itemName")
            itemDistance = it.getDouble("itemDistance", 0.0)
            itemBuilding = it.getString("itemBuilding")
            message = it.getString("message")

            val sharedPref = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                username?.let { putString("username", username) }
                itemName?.let { putString("itemName", itemName) }
                itemDistance?.let { putFloat("itemDistance", it.toFloat()) }
                itemBuilding?.let { putString("itemBuilding", itemBuilding) }
                message?.let { putString("message", message) }
                apply() // Apply changes asynchronously
            }
        }

        val sharedPref = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        username = sharedPref.getString("username", null)
        itemName = sharedPref.getString("itemName", null)
        itemDistance = sharedPref.getFloat("itemDistance", 0f).toDouble()
        itemBuilding = sharedPref.getString("itemBuilding", null)
        message = sharedPref.getString("message", null)

        val displayMessage = "Username: $username\nItem: $itemName\nDistance: ${"%.1f".format(itemDistance)} mi from $itemBuilding\nMessage: $message"
        val itemList = listOf(displayMessage)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, itemList)
       // binding.offersListSent.adapter = adapter

        // Hide placeholder message if data is available
        binding.textOffersSent.visibility = View.GONE
        //Function to go to sent page from sent button
        binding.receivedButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_sentFragment_to_receivedFragment)
        }
        binding.completedButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_sentFragment_to_offersFragment)
        }

        return binding.root
    }

    /* to go back into offers_naviagtion     <androidx.fragment.app.FragmentContainerView
        android:id="@+id/offersfragmentContainerView"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="409dp"
        android:layout_height="673dp"
        app:defaultNavHost="true"
        app:navGraph="@navigation/offersFragment"
        tools:layout_editor_absoluteX="1dp"
        tools:layout_editor_absoluteY="1dp"
        tools:ignore="MissingConstraints"
        />*/
    override fun onPause() {
        super.onPause()

        // Save data to SharedPreferences when the fragment is paused or the screen is about to be changed
        val sharedPref = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("username", username)
            putString("itemName", itemName)
            putFloat("itemDistance", itemDistance?.toFloat() ?: 0f)
            putString("itemBuilding", itemBuilding)
            putString("message", message)
            apply() // Apply changes asynchronously
        }
    }
}

/*package com.example.lunchbox.ui.offers

import android.os.Bundle
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lunchbox.R
import com.example.lunchbox.databinding.FragmentOffersSentBinding
import android.widget.ArrayAdapter


class OffersSent : Fragment() {

    private lateinit var binding: FragmentOffersSentBinding

    private var username: String? = null
    private var itemName: String? = null
    private var itemDistance: Double? = null
    private var itemBuilding: String? = null
    private var message: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        /*binding = FragmentOffersSentBinding.inflate(inflater, container, false)


        binding.receivedButton.setOnClickListener {*/

        arguments?.let {
            username = it.getString("username")
            itemName = it.getString("itemName")
            itemDistance = it.getDouble("itemDistance", 0.0)
            itemBuilding = it.getString("itemBuilding")
            message = it.getString("message")

            val sharedPref = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                username?.let { putString("username", username) }
                itemName?.let { putString("itemName", itemName) }
                itemDistance?.let { putFloat("itemDistance", it.toFloat()) }
                itemBuilding?.let { putString("itemBuilding", itemBuilding) }
                message?.let { putString("message", message) }
                apply() // Apply changes asynchronously
            }
        }

        val sharedPref = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        username = sharedPref.getString("username", null)
        itemName = sharedPref.getString("itemName", null)
        itemDistance = sharedPref.getFloat("itemDistance", 0f).toDouble()
        itemBuilding = sharedPref.getString("itemBuilding", null)
        message = sharedPref.getString("message", null)

        val displayMessage = "Username: $username\nItem: $itemName\nDistance: ${"%.1f".format(itemDistance)} mi from $itemBuilding\nMessage: $message"
        val itemList = listOf(displayMessage)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, itemList)
        binding.offersListSent.adapter = adapter

        // Hide placeholder message if data is available
        binding.textOffersSent.visibility = View.GONE
        //Function to go to sent page from sent button
        binding.receivedButton.setOnClickListener{

            it.findNavController().navigate(R.id.action_sentFragment_to_receivedFragment)
        }

        binding.completedButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_sentFragment_to_offersFragment)
        }

        return binding.root
    }

    /* to go back into offers_naviagtion     <androidx.fragment.app.FragmentContainerView
        android:id="@+id/offersfragmentContainerView"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="409dp"
        android:layout_height="673dp"
        app:defaultNavHost="true"
        app:navGraph="@navigation/offersFragment"
        tools:layout_editor_absoluteX="1dp"
        tools:layout_editor_absoluteY="1dp"
        tools:ignore="MissingConstraints"
        />*/
    override fun onPause() {
        super.onPause()

        // Save data to SharedPreferences when the fragment is paused or the screen is about to be changed
        val sharedPref = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("username", username)
            putString("itemName", itemName)
            putFloat("itemDistance", itemDistance?.toFloat() ?: 0f)
            putString("itemBuilding", itemBuilding)
            putString("message", message)
            apply() // Apply changes asynchronously
        }
    }
}

*/