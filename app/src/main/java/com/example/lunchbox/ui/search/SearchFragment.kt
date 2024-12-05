package com.example.lunchbox.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lunchbox.databinding.FragmentSearchBinding
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArrayAdapter<String>
    private val data = arrayOf("Apple", "Banana", "Cherry", "Date", "Grape", "Orange", "Dr. Pepper", "Milk", "Spaghetti Noodles", "Raisins", "Flour", "Sugar", "Cumin", "Apple Sauce", "Peanut Butter")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, data)
        binding.listView.adapter = adapter

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}