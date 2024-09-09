package com.example.altntakip.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.altntakip.R
import com.example.altntakip.viewmodel.JewelryDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JewelryDetailsFragment : Fragment() {

    private val viewModel : JewelryDetailsViewModel by viewModels()

    private var id : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        id  = arguments?.getString("id")
        println("id : "+id)
        return inflater.inflate(R.layout.fragment_jewelry_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
         viewModel.getVillaById(id ?: "id")
        viewModel.liveDataJewelry.observe(viewLifecycleOwner) {
            println("it : "+it)
        }

    }

}

// TODO: ID argüman ile gönderiliyor ama alım yapılamıoyor nedni belirsiz