package com.example.altntakip.model
import com.google.gson.annotations.SerializedName

data class GoldPriceResponse(
    val timestamp: Long,
    val metal: String,
    val currency: String,
    val exchange: String,
    val symbol: String,
    val prev_close_price: Double,
    val open_price: Double,
    val low_price: Double,
    val high_price: Double,
    val open_time: Long,
    val price: Double,
    val ch: Double,
    val chp: Double,
    val ask: Double,
    val bid: Double,
    val price_gram_24k: Double,
    val price_gram_22k: Double,
    val price_gram_21k: Double,
    val price_gram_20k: Double,
    val price_gram_18k: Double,
    val price_gram_16k: Double,
    val price_gram_14k: Double,
    val price_gram_10k: Double
)