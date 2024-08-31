package com.example.altntakip.model

import com.google.gson.annotations.SerializedName

data class FinanceResponse(
    @SerializedName("USD") val usd: CurrencyData,
    @SerializedName("EUR") val eur: CurrencyData,
    @SerializedName("GBP") val gbp: CurrencyData,
    @SerializedName("GRA") val gra: CurrencyData,
    // diğer dövizler veya veri alanları
)

data class CurrencyData(
    @SerializedName("name") val name: String,
    @SerializedName("Buying") val buying: String,
    @SerializedName("Selling") val selling: String,
    @SerializedName("Change") val change: String
)
