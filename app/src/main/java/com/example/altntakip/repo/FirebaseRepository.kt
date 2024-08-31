package com.example.altntakip.repo

import com.example.altntakip.model.JewelryModel
import com.google.android.gms.tasks.Task

interface FirebaseRepository {


    // Adding Jewelry to Firestore
    fun addJewelryToFirestore(jewelryId: String, jewelry: JewelryModel): Task<Void>
}