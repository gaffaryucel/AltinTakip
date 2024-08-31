package com.example.altntakip.api

import com.example.altntakip.model.FinanceResponse
import com.example.altntakip.model.GoldPriceResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GoldPriceApi {
    @GET("{metal}/USD")
    suspend fun getGoldPrice(@Path("metal") metal: String): Response<GoldPriceResponse>
}

interface FinanceService {
    @GET("v4/today.json")
    fun getTodayData(): Call<FinanceResponse>
}
