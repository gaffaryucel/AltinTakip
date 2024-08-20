package com.example.altntakip.repo

import com.example.altntakip.api.GoldPriceApi
import com.example.altntakip.api.StockMarketApi
import com.example.altntakip.model.AlphaVantageResponse
import com.example.altntakip.model.GoldPriceResponse
import retrofit2.Response
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val stockMarketApi : StockMarketApi,
    private val goldPriceApi: GoldPriceApi
) : ApiRepository {
    override suspend fun getMarketPrice(function  :String,symbol: String, interval: String, apiKey: String): Response<AlphaVantageResponse> {
        return stockMarketApi.getGoldPrice(function = function,symbol = symbol, interval = interval, apiKey = apiKey)
    }
    override suspend fun getGoldPrice(): Response<GoldPriceResponse> {
        return goldPriceApi.getGoldPrice()
    }
}