package com.example.altntakip.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.altntakip.R
import com.example.altntakip.adapter.GoldAdapter
import com.example.altntakip.adapter.JewelryAdapter
import com.example.altntakip.adapter.JewelryImageAdapter
import com.example.altntakip.databinding.FragmentAddJewelryBinding
import com.example.altntakip.databinding.FragmentHomeBinding
import com.example.altntakip.model.JewelryModel
import com.example.altntakip.viewmodel.AddJewelryViewModel
import com.example.altntakip.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class AddJewelryFragment : Fragment() {

    // Binding değişkeni tanımlayın
    private lateinit var binding: FragmentAddJewelryBinding
    private val viewModel : AddJewelryViewModel by viewModels()
    private lateinit var jewelryImageAdapter: JewelryImageAdapter

    private var selectedCoverImage: Uri? = null
    private lateinit var coverImageLauncher: ActivityResultLauncher<Intent>
    private val selectedOtherImages = mutableListOf<Uri>()
    private lateinit var otherImageLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddJewelryBinding.inflate(inflater, container, false)
        setupLaunchers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickItems()

        binding.rvOtherJewelryImage.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        jewelryImageAdapter = JewelryImageAdapter()
        binding.rvOtherJewelryImage.adapter = jewelryImageAdapter

        // Gender Spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerGender.adapter = adapter
        }

        // Metal Type Spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.metal_type_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerMetalType.adapter = adapter
        }

        // Category Spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.adapter = adapter
        }

        binding.btnSave.setOnClickListener {
            createJewelryModelFromInput().also {
                if (selectedCoverImage != null){
                    viewModel.uploadJewelryImages(it,selectedCoverImage!!,selectedOtherImages,UUID.randomUUID().toString())
                }
            }
        }


    }

    private fun setClickItems() {
        with(binding) {
            ivJewelryCover.setOnClickListener {
                openCoverImagePicker()
            }

            ivOtherJewelryImage.setOnClickListener {
                openOtherImagePicker()
            }
        }
    }

    private fun openCoverImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        coverImageLauncher.launch(intent)
    }

    private fun openOtherImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        otherImageLauncher.launch(intent)
    }

    private fun setupLaunchers() {
        coverImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { image ->
                        selectedCoverImage = image
                        binding.ivJewelryCover.setImageURI(image) // Resmi göster
                    }
                }
            }

        otherImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.clipData?.let { data ->
                        for (i in 0 until data.itemCount) {
                            val imageUri = data.getItemAt(i).uri
                            selectedOtherImages.add(imageUri)
                            jewelryImageAdapter.imageUriList = selectedOtherImages
                            jewelryImageAdapter.notifyDataSetChanged()
                        }
                        // Seçilen diğer resimleri göster (örneğin, RecyclerView ile)
                    } ?: result.data?.data?.let { image ->
                        selectedOtherImages.add(image)
                    }
                }
            }
    }



    private fun createJewelryModelFromInput(): JewelryModel {
        // EditText'lerden verileri alıyoruz
        val id = UUID.randomUUID().toString()
        val name = binding.etJewelryName.text.toString()
        val price = binding.etJewelryPrice.text.toString().toDoubleOrNull() ?: 0.0
        val discountPercentage = binding.etDiscountPercentage.text.toString().toIntOrNull()
        val metalType = binding.spinnerMetalType.selectedItem.toString()
        val karat = binding.etKarat.text.toString().toIntOrNull() ?: 0
        val weight = binding.etWeight.text.toString().toDoubleOrNull()
        val gemstone = binding.etGemstone.text.toString()
        val gender = binding.spinnerGender.selectedItem.toString()
        val category = binding.spinnerCategory.selectedItem.toString()
        val inStock = binding.switchInStock.isChecked
        val rating = binding.etRating.text.toString().toDoubleOrNull()
        val reviewsCount = binding.etReviewsCount.text.toString().toIntOrNull()
        val material = binding.etMaterial.text.toString()
        val brand = binding.etBrand.text.toString()
        val description = binding.etDescription.text.toString()

        // JewelryModel'i oluşturuyoruz
        return JewelryModel(
            id = id,
            name = name,
            price = price,
            discountPercentage = discountPercentage,
            metalType = metalType,
            karat = karat,
            weight = weight,
            gemstone = if (gemstone.isNotBlank()) gemstone else null,
            gender = gender,
            category = category,
            inStock = inStock,
            rating = rating,
            reviewsCount = reviewsCount,
            material = if (material.isNotBlank()) material else null,
            brand = if (brand.isNotBlank()) brand else null,
            description = if (description.isNotBlank()) description else null
        )
    }




}