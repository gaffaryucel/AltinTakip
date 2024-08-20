package com.example.altntakip.model

import com.google.gson.annotations.SerializedName

// MetaData sınıfı
data class MetaData(
    @SerializedName("1. Information")
    val information: String,
    @SerializedName("2. Symbol")
    val symbol: String,
    @SerializedName("3. Last Refreshed")
    val lastRefreshed: String,
    @SerializedName("4. Interval")
    val interval: String,
    @SerializedName("5. Output Size")
    val outputSize: String,
    @SerializedName("6. Time Zone")
    val timeZone: String
)

// TimeSeriesData sınıfı
data class TimeSeriesData(
    @SerializedName("1. open")
    val open: String,
    @SerializedName("2. high")
    val high: String,
    @SerializedName("3. low")
    val low: String,
    @SerializedName("4. close")
    val close: String,
    @SerializedName("5. volume")
    val volume: String
)

// AlphaVantageResponse sınıfı
data class AlphaVantageResponse(
    @SerializedName("Meta Data")
    val metaData: MetaData,
    @SerializedName("Time Series (5min)")
    val timeSeries: Map<String, TimeSeriesData> // Zaman serisi verileri, tarih ile eşleştirilmiş
)
