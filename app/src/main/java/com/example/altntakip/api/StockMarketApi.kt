package com.example.altntakip.api

import com.example.altntakip.model.AlphaVantageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockMarketApi {
    @GET("/query")
    suspend fun getGoldPrice(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("symbol") symbol: String,
        @Query("interval") interval: String = "5min",
        @Query("apikey") apiKey: String
    ): Response<AlphaVantageResponse>
}