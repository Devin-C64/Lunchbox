package com.example.lunchbox.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lunchbox.databinding.FragmentSearchBinding
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import com.example.lunchbox.R
import androidx.navigation.fragment.findNavController
import com.example.lunchbox.ui.message.MessageFragment



class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var itemList: List<Item>
    private lateinit var displayList: MutableList<String>
    private lateinit var filteredItemList: List<Item> // Track filtered items directly
    private var showingItems = true
    private var filteredUsers: List<Item> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loadDataFromJson()

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, displayList)
        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { _, _, position, _ ->
            if (showingItems) {
                val selectedItem = filteredItemList[position]  // Get the actual Item object
                showUsersWithItem(selectedItem.name)
            } else {
                val selectedUser = filteredUsers[position]
                openMessageFragment(selectedUser.username)
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                updateFilteredItems(newText)
                return false
            }
        })

        return root
    }

    private fun loadDataFromJson() {
        try {
            val inputStream = requireContext().assets.open("data.json")
            val reader = InputStreamReader(inputStream)
            val itemType = object : TypeToken<List<Item>>() {}.type
            itemList = Gson().fromJson(reader, itemType)

            // Populate display list with item names
            displayList = itemList.map { it.name }.distinct().toMutableList()
            filteredItemList = itemList.distinctBy { it.name } // Initialize filtered list
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error loading data", Toast.LENGTH_SHORT).show()
            itemList = emptyList()
            displayList = mutableListOf()
            filteredItemList = emptyList()
        }
    }

    private fun updateFilteredItems(query: String?) {
        filteredItemList = if (query.isNullOrEmpty()) {
            itemList.distinctBy { it.name }
        } else {
            itemList.filter { it.name.startsWith(query, ignoreCase = true) }.distinctBy { it.name }
        }

        displayList.clear()
        displayList.addAll(filteredItemList.map { it.name })
        adapter.notifyDataSetChanged()

        // Reset to item mode and clear filtered users
        showingItems = true
        filteredUsers = emptyList()
    }

    private fun showUsersWithItem(itemName: String) {
        // Filter users who have the selected item
        filteredUsers = itemList.filter { it.name == itemName }
        val users = filteredUsers.map {
            "${it.username} (${it.distance} mi from ${it.closestSchoolBuilding})"
        }

        if (users.isEmpty()) {
            Toast.makeText(requireContext(), "No users found with this item", Toast.LENGTH_SHORT).show()
            return
        }

        // Update the adapter's data with the list of users
        displayList.clear()
        displayList.addAll(users)
        adapter.notifyDataSetChanged()
        showingItems = false
    }

    private fun resetToItemList() {
        // Reset the adapter's data to the list of item names
        displayList.clear()
        displayList.addAll(itemList.map { it.name }.distinct())
        adapter.notifyDataSetChanged()
        showingItems = true
        filteredUsers = emptyList()
    }

    private fun openMessageFragment(username: String) {
        val bundle = Bundle()
        bundle.putString("username", username)

        findNavController().navigate(R.id.navigation_message, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
