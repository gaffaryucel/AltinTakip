package com.example.altntakip.util

import android.util.Log
import com.example.altntakip.model.JewelryModel
import com.google.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot.toJewelry(): JewelryModel? = try {
    toObject(JewelryModel::class.java)
} catch (e: Exception) {
    e.message?.let {
        Log.e(
            "getVilla",
            it
        )
    }
    JewelryModel()
}
