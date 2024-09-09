package com.example.altntakip.model

data class JewelryModel(
    val id: String? = null,
    val name: String? = null,
    val price: Double? = null,
    val discountPercentage: Int? = null,
    val metalType: String? = null,
    val karat: Int? = null,
    val weight: Double? = null,
    val gemstone: String? = null,
    val gender: String? = null,
    val category: String? = null,
    var imageUrl: String? = null,
    var jewelryImages: List<String>? = null,
    val inStock: Boolean? = null,
    val rating: Double? = null,
    val reviewsCount: Int? = null,
    val material: String? = null,
    val brand: String? = null,
    val description : String? = null
)