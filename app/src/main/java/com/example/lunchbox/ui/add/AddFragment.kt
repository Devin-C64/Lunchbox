package com.example.lunchbox.ui.add

import android.app.DatePickerDialog
import android.app.FragmentManager
import android.content.Intent
import android.content.pm.PackageManager
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
import com.example.lunchbox.R
import com.example.lunchbox.camera.CameraFragment
import com.example.lunchbox.databinding.FragmentAddBinding
import com.example.lunchbox.ui.home.HomeFragment
import java.io.File
import java.time.Month

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
//            val fragManager = requireActivity().supportFragmentManager
//            val transaction = fragManager.beginTransaction()
//            transaction.replace(R.id.navigation_add,
//                fragManager.findFragmentById(R.id.navigation_home)!!
//            )
//            transaction.commitNowAllowingStateLoss()
//            transaction.commit()
        }

        binding.captureImgBtn.setOnClickListener {
//            val fragManager = requireActivity().supportFragmentManager
//            val transaction = fragManager.beginTransaction()
//            transaction.replace(
//                R.id.navigation_camera,
//                findViewTreeLifecycleOwner(R.id.navigation_add)
//            )
//            transaction.addToBackStack('R.id.navigation_add') // if u want this fragment to stay in stack specify it
//            transaction.commit()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}