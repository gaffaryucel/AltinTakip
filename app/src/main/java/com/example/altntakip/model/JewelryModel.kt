package com.example.altntakip.model

data class JewelryModel(
    val id: String,                        // Takı ürünü için benzersiz kimlik
    val name: String,                      // Takının ismi (örneğin: 14KT Yellow Gold Diamond Hoop)
    val price: Double,                     // Takının fiyatı (örneğin: 23145.0)
    val discountPercentage: Int?,          // İndirim yüzdesi (örneğin: 30, opsiyonel)
    val metalType: String,                 // Metal türü (örneğin: Gold, Silver, Platinum)
    val karat: Int,                        // Metalin ayarı (örneğin: 14, 18, 24)
    val weight: Double?,                   // Takının ağırlığı (gram cinsinden, opsiyonel)
    val gemstone: String?,                 // Taş türü (örneğin: Diamond, Ruby, opsiyonel)
    val gender: String,                    // Hangi cinsiyet için (örneğin: Women, Men, Unisex)
    val category: String,                  // Kategori (örneğin: Earrings, Necklace)
    val imageUrl: String,                  // Ürünün görsel URL'si
    val inStock: Boolean,                  // Stok durumu
    val rating: Double?,                   // Ürünün değerlendirme puanı (örneğin: 4.7, opsiyonel)
    val reviewsCount: Int?,                // Ürün için yapılan yorum sayısı (opsiyonel)
    val material: String?,                 // Malzeme bilgisi (örneğin: Gold-Plated, opsiyonel)
    val brand: String?,                    // Markası (örneğin: XYZ Jewelers, opsiyonel)
    val description: String?               // Ürün açıklaması (opsiyonel)
)
