package com.example.altntakip.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.altntakip.adapter.GoldAdapter
import com.example.altntakip.api.FinanceService
import com.example.altntakip.databinding.FragmentHomeBinding
import com.example.altntakip.model.CurrencyData
import com.example.altntakip.model.FinanceResponse
import com.example.altntakip.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel : HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var goldAdapter: GoldAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView'ı yatay olarak ayarlayın
        binding.recyclerViewGold.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // PagerSnapHelper'ı oluşturun ve RecyclerView'a ekleyin
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerViewGold)

        // Adapter'ı oluşturun ve RecyclerView'a bağlayın
        goldAdapter = GoldAdapter()
        binding.recyclerViewGold.adapter = goldAdapter

        // Adapter altına indicator eklendi
        binding.circleindicator.attachToRecyclerView(binding.recyclerViewGold,snapHelper)
        goldAdapter.registerAdapterDataObserver(binding.circleindicator.adapterDataObserver)


        viewModel.getGoldData()

        // Altın fiyatlarını çek
    }

    override fun onResume() {
        super.onResume()
        observeLiveData()

    }
    private fun observeLiveData() {
        println("observe")
        // ViewModel'dan veri al ve Adapter'ı güncelle

        viewModel._goldLiveData.observe(viewLifecycleOwner) {
            goldAdapter.submitList(it)
        }
    }

}


