package com.example.altntakip.repo

import com.example.altntakip.model.AlphaVantageResponse
import com.example.altntakip.model.GoldPriceResponse
import retrofit2.Response

interface ApiRepository {
    suspend fun getMarketPrice(function : String,symbol: String, interval: String, apiKey: String): Response<AlphaVantageResponse>
    suspend fun getGoldPrice(metal : String): Response<GoldPriceResponse>

}