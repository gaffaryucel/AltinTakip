package com.example.altntakip.model

data class GoldInfo(
    val type: String,
    val purity: String,
    val weight: String,
    val currentPrice: String,
    val priceChange: String,
    val priceChangeDirection: String // "up" or "down"
)

