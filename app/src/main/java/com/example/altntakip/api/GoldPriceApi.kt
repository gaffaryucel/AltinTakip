package com.example.altntakip.api

import com.example.altntakip.model.GoldPriceResponse
import retrofit2.Response
import retrofit2.http.GET

interface GoldPriceApi {
    @GET("XAU/USD")
    suspend fun getGoldPrice(): Response<GoldPriceResponse>
}