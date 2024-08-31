package com.example.altntakip.viewmodel

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.altntakip.R
import com.example.altntakip.databinding.FragmentHomeBinding
import com.example.altntakip.model.AlphaVantageResponse
import com.example.altntakip.model.CurrencyData
import com.example.altntakip.model.FinanceResponse
import com.example.altntakip.model.GoldInfo
import com.example.altntakip.model.GoldPriceResponse
import com.example.altntakip.repo.ApiRepository
import com.example.altntakip.util.CurrencyType
import com.example.altntakip.util.Metals
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// HomeViewModel.kt
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val goldRepository: ApiRepository
) : ViewModel() {

    private val _goldPriceLiveData = MutableLiveData<List<GoldInfo>?>()
    val goldPriceLiveData: LiveData<List<GoldInfo>?> get() = _goldPriceLiveData

    val _goldLiveData = MutableLiveData<List<GoldInfo>?>()

    private val _silverPriceLiveData = MutableLiveData<GoldInfo>()
    val silverPriceLiveData: LiveData<GoldInfo> get() = _silverPriceLiveData

    private val _platinumPriceLiveData = MutableLiveData<GoldInfo>()
    val platinumPriceLiveData: LiveData<GoldInfo> get() = _platinumPriceLiveData

    private val _palladiumPriceLiveData = MutableLiveData<GoldInfo>()
    val palladiumPriceLiveData: LiveData<GoldInfo> get() = _palladiumPriceLiveData


    fun getGoldData() = viewModelScope.launch{
        val call = goldRepository.getGoldData()
        call.enqueue(object : Callback<FinanceResponse> {
            override fun onResponse(call: Call<FinanceResponse>, response: Response<FinanceResponse>) {
                if (response.isSuccessful) {
                    val financeData = response.body()
                    val list = ArrayList<GoldInfo>()
                    val gold = GoldInfo(
                        financeData?.gra?.name.toString(),
                        financeData?.gra?.buying.toString(),
                        financeData?.gra?.selling.toString(),
                        financeData?.gra?.change.toString(),
                        CurrencyType.GOLD
                    )
                    val usd = GoldInfo(
                        financeData?.usd?.name.toString(),
                        financeData?.usd?.buying.toString(),
                        financeData?.usd?.selling.toString(),
                        financeData?.usd?.change.toString(),
                        CurrencyType.USD
                    )
                    val eur = GoldInfo(
                        financeData?.eur?.name.toString(),
                        financeData?.eur?.buying.toString(),
                        financeData?.eur?.selling.toString(),
                        financeData?.eur?.change.toString(),
                        CurrencyType.EUR
                    )

                    list.add(gold)
                    list.add(usd)
                    list.add(eur)

                    _goldLiveData.value = list
                } else {
                    // Hata durumunu işleyin
                    println("Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<FinanceResponse>, t: Throwable) {
                // Hata durumunu işleyin
                println("Failure: ${t.message}")
            }
        })
    }

}
