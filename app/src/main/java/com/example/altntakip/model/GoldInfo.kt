package com.example.altntakip.model

import com.example.altntakip.util.CurrencyType

data class GoldInfo(
    val name: String,
    val buying: String,
    val selling: String,
    val change: String,
    val type: CurrencyType,
)

