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


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var itemList: List<Item>
    private lateinit var displayList: MutableList<String>
    private var showingItems = true

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
                val selectedItemName = displayList[position]
                showUsersWithItem(selectedItemName)
            } else {
                resetToItemList()
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Optionally handle search submission
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
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

            // Populate display list with formatted strings
            displayList = itemList.map { it.name }.distinct().toMutableList()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error loading data", Toast.LENGTH_SHORT).show()
            itemList = emptyList()
            displayList = mutableListOf()
        }
    }

    private fun showUsersWithItem(itemName: String) {
        // Filter users who have the selected item
        val users = itemList
            .filter { it.name == itemName }
            .map { "${it.username} (${it.distance} mi from ${it.closestSchoolBuilding})" }

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}