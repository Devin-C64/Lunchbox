package com.example.lunchbox.ui.add

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lunchbox.R
import com.example.lunchbox.databinding.FragmentAddBinding
import java.io.ByteArrayOutputStream



class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        // Toast for Save Draft
        binding.saveDraftbtn.setOnClickListener {
            Toast.makeText(requireContext(), "Draft Saved", Toast.LENGTH_SHORT).show()
        }

        // Toast for Post Item
        binding.postItemBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Item Posted", Toast.LENGTH_SHORT).show()

            val itemNameText : EditText = requireView().findViewById(R.id.item_name_edit)
            val itemName = itemNameText.text.toString()

            val itemQuantText : EditText = requireView().findViewById(R.id.quantity_edit)
            val itemQuant = itemQuantText.text.toString()

            val itemExpiryDate : DatePicker = requireView().findViewById(R.id.date_picker_container)
            val itemExpiry = (itemExpiryDate.month+1).toString() + "/" + itemExpiryDate.dayOfMonth.toString() + "/" + itemExpiryDate.year.toString()

            val itemTagsText : EditText = requireView().findViewById(R.id.item_tags_edit)
            val itemTags = itemTagsText.text.toString()

            val image: ImageView = binding.previewView
            val imageBundle = image.drawToBitmap()
            val stream = ByteArrayOutputStream()
            imageBundle.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()


            // Bundle data to send to Profile fragment
            val bundle = Bundle()
            bundle.putInt("postedItem", 1)
            bundle.putString("itemName", itemName)
            bundle.putString("itemQuant", itemQuant)
            bundle.putString("itemExpiry", itemExpiry)
            bundle.putString("itemTags", itemTags)
            bundle.putByteArray("image",byteArray)
            //bundle.putString("image", imageString)

            it.findNavController().navigate(R.id.navigation_profile, bundle)  // Navigating to 'Profile' page
//
//            val newFrag = HomeFragment()
//            val fragManager = requireActivity().supportFragmentManager
//            val transaction = fragManager.beginTransaction()
//            transaction.replace(R.id.navigation_add, newFrag).commit()
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

        return binding.root
    }
}
/*
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
            it.findNavController().navigate(R.id.action_offers_to_sent)  // Navigating to 'Sent' page
//
//            val newFrag = HomeFragment()
//            val fragManager = requireActivity().supportFragmentManager
//            val transaction = fragManager.beginTransaction()
//            transaction.replace(R.id.navigation_add, newFrag).commit()
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

*/

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
