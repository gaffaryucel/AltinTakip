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
import com.example.altntakip.model.GoldInfo
import com.example.altntakip.model.GoldPriceResponse
import com.example.altntakip.repo.ApiRepository
import com.example.altntakip.util.Metals
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

// HomeViewModel.kt
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val goldRepository: ApiRepository
) : ViewModel() {

    private val _goldPriceLiveData = MutableLiveData<List<GoldInfo>?>()
    val goldPriceLiveData: LiveData<List<GoldInfo>?> get() = _goldPriceLiveData

    private val _silverPriceLiveData = MutableLiveData<GoldInfo>()
    val silverPriceLiveData: LiveData<GoldInfo> get() = _silverPriceLiveData

    private val _platinumPriceLiveData = MutableLiveData<GoldInfo>()
    val platinumPriceLiveData: LiveData<GoldInfo> get() = _platinumPriceLiveData

    private val _palladiumPriceLiveData = MutableLiveData<GoldInfo>()
    val palladiumPriceLiveData: LiveData<GoldInfo> get() = _palladiumPriceLiveData

    fun getStockMarketPrice(binding : FragmentHomeBinding,context : Context) {
        viewModelScope.launch {
            try {
                val response = goldRepository.getMarketPrice(
                    "TIME_SERIES_INTRADAY",
                    "IBM",
                    "5min",
                    "R4KA7QNU8EGFD7JQ"
                )

                if (response.isSuccessful) {
                    response.body()?.let { alphaVantageResponse ->
                        val metaData = alphaVantageResponse.metaData
                        val timeSeries = alphaVantageResponse.timeSeries

                        // Eğer timeSeries boş değilse veriyi işleyin
                        timeSeries?.let {
                            // Örneğin, son 30 günün verilerini almak
                            val last30DaysData = it.entries
                                .sortedByDescending { entry -> entry.key }
                                .take(30)

                            // Veriyi işleyip grafiğe ekleyin
                            val entries = last30DaysData.mapIndexed { index, entry ->
                                val (timestamp, data) = entry
                                Entry(index.toFloat(), data.close.toFloat())
                            }

                            val lineDataSet = LineDataSet(entries, "Altın Fiyatı").apply {
                                color = Color.GREEN
                                valueTextColor = Color.BLACK
                                lineWidth = 2f
                                setDrawFilled(true)
                                fillDrawable = ContextCompat.getDrawable(context, R.drawable.chart_fill)
                            }

                            val lineData = LineData(lineDataSet)

                            binding.lineChart.apply {
                                data = lineData
                                xAxis.valueFormatter = IndexAxisValueFormatter(last30DaysData.map { it.key })
                                xAxis.labelRotationAngle = 45f
                                xAxis.position = XAxis.XAxisPosition.BOTTOM
                                description.isEnabled = false
                                legend.isEnabled = true
                                axisRight.isEnabled = false
                                axisLeft.setDrawGridLines(false)
                                axisLeft.axisMinimum = entries.minOf { it.y }
                                axisLeft.axisMaximum = entries.maxOf { it.y }
                                setVisibleXRangeMaximum(30f)
                                isDragEnabled = true
                                isScaleXEnabled = true
                                isScaleYEnabled = true
                                invalidate()
                            }
                        } ?: run {
                            println("Veri bulunamadı.")
                        }
                    }
                } else {
                    println("API çağrısı başarısız oldu: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
            }
        }
    }

    fun fetchGoldPrice(metal: String) = viewModelScope.launch {
        try {
            val response = goldRepository.getGoldPrice(metal)
            if (response.isSuccessful) {
                val goldPrice = response.body()
                if (goldPrice != null) {
                    _goldPriceLiveData.value = mapGoldPriceResponseToGoldInfo(goldPrice)
                } else {
                    // Gold price body is null
                    println("Error: Received null response body")
                }
            } else {
                // API response not successful
                println("Error: API call failed with status code ${response.code()}")
                println("Error message: ${response.message()}")
                response.errorBody()?.let { errorBody ->
                    println("Error body: ${errorBody.string()}")
                }
            }
        } catch (e: Exception) {
            // Handle any exceptions thrown during the API call
            println("Error: Exception occurred during API call: ${e.localizedMessage}")
        }
    }

    fun fetchMetalPrices() = viewModelScope.launch{
        val response1 = goldRepository.getGoldPrice(Metals.XAG.toString())
        if (response1.isSuccessful) {
            val price = response1.body()
            println(price)
        }

    }//sjafglkfdjhngçövcbmşljetgmlkxföm

    private fun mapGoldPriceResponseToGoldInfo(goldPrice: GoldPriceResponse): List<GoldInfo> {
        val price24k = goldPrice.price_gram_24k
        val price22k = goldPrice.price_gram_22k
        val price21k = goldPrice.price_gram_21k
        val price18k = goldPrice.price_gram_18k

        val priceChange = goldPrice.ch
        val priceChangePercentage = goldPrice.chp

        // Fiyat değişiminin yönünü belirleme
        val priceChangeDirection = if (priceChange >= 0) "up" else "down"

        return listOf(
            GoldInfo(
                type = "24 Ayar Gram Altın",
                purity = "Saflık: %99.9",
                weight = "Ağırlık: 1 gram",
                priceChange = "${String.format("%.2f", priceChange)} USD (${String.format("%.2f", priceChangePercentage)}%)",
                currentPrice = "${String.format("%.2f", price24k)} USD",
                priceChangeDirection = priceChangeDirection
            ),
            GoldInfo(
                type = "22 Ayar Gram Altın",
                purity = "Saflık: %91.6",
                weight = "Ağırlık: 1 gram",
                priceChange = "${String.format("%.2f", priceChange)} USD (${String.format("%.2f", priceChangePercentage)}%)",
                currentPrice = "${String.format("%.2f", price22k)} USD",
                priceChangeDirection = priceChangeDirection
            ),
            GoldInfo(
                type = "21 Ayar Gram Altın",
                purity = "Saflık: %87.5",
                weight = "Ağırlık: 1 gram",
                priceChange = "${String.format("%.2f", priceChange)} USD (${String.format("%.2f", priceChangePercentage)}%)",
                currentPrice = "${String.format("%.2f", price21k)} USD",
                priceChangeDirection = priceChangeDirection
            ),
            GoldInfo(
                type = "18 Ayar Gram Altın",
                purity = "Saflık: %75.0",
                weight = "Ağırlık: 1 gram",
                priceChange = "${String.format("%.2f", priceChange)} USD (${String.format("%.2f", priceChangePercentage)}%)",
                currentPrice = "${String.format("%.2f", price18k)} USD",
                priceChangeDirection = priceChangeDirection
            )
        )
    }
    private fun mapSilverPriceResponseToGoldInfo(silverPrice: GoldPriceResponse) {
        val priceGram = silverPrice.price
        val priceChange = silverPrice.ch
        val priceChangePercentage = silverPrice.chp

        // Fiyat değişiminin yönünü belirleme
        val priceChangeDirection = if (priceChange >= 0) "up" else "down"

        _silverPriceLiveData.value = GoldInfo(
                type = "Gram Gümüş",
                purity = "Saflık: %99.9",
                weight = "Ağırlık: 1 gram",
                priceChange = "${String.format("%.2f", priceChange)} USD (${String.format("%.2f", priceChangePercentage)}%)",
                currentPrice = "${String.format("%.2f", priceGram)} USD",
                priceChangeDirection = priceChangeDirection
        )
    }
    private fun mapPlatinumPriceResponseToGoldInfo(platinumPrice: GoldPriceResponse) {
        val priceGram = platinumPrice.price
        val priceChange = platinumPrice.ch
        val priceChangePercentage = platinumPrice.chp

        // Fiyat değişiminin yönünü belirleme
        val priceChangeDirection = if (priceChange >= 0) "up" else "down"

            _platinumPriceLiveData.value = GoldInfo(
                type = "Gram Platin",
                purity = "Saflık: %99.9",
                weight = "Ağırlık: 1 gram",
                priceChange = "${String.format("%.2f", priceChange)} USD (${String.format("%.2f", priceChangePercentage)}%)",
                currentPrice = "${String.format("%.2f", priceGram)} USD",
                priceChangeDirection = priceChangeDirection
            )
    }
    private fun mapPalladiumPriceResponseToGoldInfo(palladiumPrice: GoldPriceResponse) {
        val priceGram = palladiumPrice.price
        val priceChange = palladiumPrice.ch
        val priceChangePercentage = palladiumPrice.chp

        // Fiyat değişiminin yönünü belirleme
        val priceChangeDirection = if (priceChange >= 0) "up" else "down"

        _palladiumPriceLiveData.value = GoldInfo(
            type = "Gram Paladyum",
            purity = "Saflık: %99.9",
            weight = "Ağırlık: 1 gram",
            priceChange = "${String.format("%.2f", priceChange)} USD (${String.format("%.2f", priceChangePercentage)}%)",
            currentPrice = "${String.format("%.2f", priceGram)} USD",
            priceChangeDirection = priceChangeDirection
        )
    }

}
