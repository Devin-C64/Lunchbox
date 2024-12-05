package com.example.lunchbox.ui.add

import android.app.Activity
import android.app.DatePickerDialog
import android.app.FragmentManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.findNavController
import com.example.lunchbox.R
import com.example.lunchbox.databinding.FragmentAddBinding
import com.example.lunchbox.ui.home.HomeFragment
import java.io.File
import java.time.Month

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val addViewModel =
            ViewModelProvider(this).get(AddViewModel::class.java)

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.itemNameEdit
        addViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Toast for Save Draft
        binding.saveDraftbtn.setOnClickListener {
            Toast.makeText(requireContext(), "Draft Saved", Toast.LENGTH_SHORT).show()
        }

        // Toast for Post Item
        binding.postItemBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Item Posted", Toast.LENGTH_SHORT).show()
            val newFrag = HomeFragment()
            val fragManager = requireActivity().supportFragmentManager
            val transaction = fragManager.beginTransaction()
            transaction.replace(R.id.navigation_add, newFrag).commit()
        }

        // Camera Functionality
        val cameraButton = binding.captureImgBtn
        val previewView = binding.previewView

        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val image = result.data?.extras?.get("data") as Bitmap
                previewView.setImageBitmap(image)
            }
        }

        cameraButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcher.launch(cameraIntent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

/*
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val addViewModel =
            ViewModelProvider(this).get(AddViewModel::class.java)

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.itemNameEdit
        addViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.saveDraftbtn.setOnClickListener{
            Toast.makeText(requireContext(), "Draft Saved", Toast.LENGTH_SHORT).show()
        }

        binding.postItemBtn.setOnClickListener{
            Toast.makeText(requireContext(), "Item Posted", Toast.LENGTH_SHORT).show()
            val newFrag = HomeFragment()
            val fragManager = requireActivity().supportFragmentManager
            val transaction = fragManager.beginTransaction()
            transaction.replace(R.id.navigation_add, newFrag).commit()
        }
/*
        binding.captureImgBtn.setOnClickListener {
            val newFrag = CameraFragment()
            val fragManager = requireActivity().supportFragmentManager
            val transaction = fragManager.beginTransaction()
            transaction.replace(R.id.navigation_add, newFrag).commit()
        }
*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
*/
