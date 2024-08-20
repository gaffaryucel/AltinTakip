package com.example.altntakip.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.altntakip.R
import com.example.altntakip.adapter.GoldAdapter
import com.example.altntakip.adapter.PriceAdapter
import com.example.altntakip.databinding.FragmentHomeBinding
import com.example.altntakip.model.AlphaVantageResponse
import com.example.altntakip.viewmodel.HomeViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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

        // ViewModel'dan veri al ve Adapter'ı güncelle
        viewModel.goldPriceLiveData.observe(viewLifecycleOwner) { goldInfoList ->
            goldAdapter.submitList(goldInfoList)
        }

        // Altın fiyatlarını çek
        viewModel.fetchGoldPrice()
    }

}


