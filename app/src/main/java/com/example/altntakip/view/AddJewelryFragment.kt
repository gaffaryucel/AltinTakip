package com.example.altntakip.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.viewModels
import com.example.altntakip.R
import com.example.altntakip.databinding.FragmentAddJewelryBinding
import com.example.altntakip.databinding.FragmentHomeBinding
import com.example.altntakip.model.JewelryModel
import com.example.altntakip.viewmodel.AddJewelryViewModel
import com.example.altntakip.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddJewelryFragment : Fragment() {

    // Binding değişkeni tanımlayın
    private lateinit var binding: FragmentAddJewelryBinding
    private val viewModel : AddJewelryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddJewelryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            val model = createJewelryModelFromInput().also {
                viewModel.addFakeJewelry(it)
            }

        }


    }

    private fun createJewelryModelFromInput(): JewelryModel {
        // EditText'lerden verileri alıyoruz
        val id = binding.etJewelryId.text.toString()
        val name = binding.etJewelryName.text.toString()
        val price = binding.etJewelryPrice.text.toString().toDoubleOrNull() ?: 0.0
        val discountPercentage = binding.etDiscountPercentage.text.toString().toIntOrNull()
        val metalType = binding.spinnerMetalType.selectedItem.toString()
        val karat = binding.etKarat.text.toString().toIntOrNull() ?: 0
        val weight = binding.etWeight.text.toString().toDoubleOrNull()
        val gemstone = binding.etGemstone.text.toString()
        val gender = binding.spinnerGender.selectedItem.toString()
        val category = binding.spinnerCategory.selectedItem.toString()
        val imageUrl = binding.etImageUrl.text.toString()
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
            imageUrl = imageUrl,
            inStock = inStock,
            rating = rating,
            reviewsCount = reviewsCount,
            material = if (material.isNotBlank()) material else null,
            brand = if (brand.isNotBlank()) brand else null,
            description = if (description.isNotBlank()) description else null
        )
    }


}