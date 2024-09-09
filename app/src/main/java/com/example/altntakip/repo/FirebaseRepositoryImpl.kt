package com.example.altntakip.repo

import com.example.altntakip.model.JewelryModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class FirebaseRepoImpl @Inject constructor(
    firestore: FirebaseFirestore,
    storage: FirebaseStorage,
) : FirebaseRepository {
    private val jewelryCollection = firestore.collection("jewelries")

    // Adding Jewelry to Firestore
    override fun addJewelryToFirestore(jewelryId: String, jewelry: JewelryModel): Task<Void> {
        return jewelryCollection.document(jewelryId).set(jewelry)
    }
    override fun getAllJewelryFromFirestore(limit: Long): Task<QuerySnapshot> {
        return jewelryCollection.limit(limit).get()
    }

    override fun getJewelryByIdFromFirestore(villaId: String): Task<DocumentSnapshot> {
        return jewelryCollection.document(villaId).get()
    }
}