package com.example.altntakip.repo

import com.example.altntakip.model.JewelryModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface FirebaseRepository {


    // Adding Jewelry to Firestore
    fun addJewelryToFirestore(jewelryId: String, jewelry: JewelryModel): Task<Void>

    fun getAllJewelryFromFirestore(limit: Long): Task<QuerySnapshot>

    fun getJewelryByIdFromFirestore(villaId: String): Task<DocumentSnapshot>
}