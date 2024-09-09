package com.example.altntakip.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.altntakip.model.JewelryModel
import com.example.altntakip.repo.FirebaseRepository
import com.example.altntakip.util.toJewelry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// TODO: Implement the ViewModel
@HiltViewModel
class JewelryDetailsViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _liveDataJewelry = MutableLiveData<JewelryModel>()
    val liveDataJewelry: LiveData<JewelryModel> get() = _liveDataJewelry

    fun getVillaById(id: String) {
        firebaseRepository.getJewelryByIdFromFirestore(id)
            .addOnSuccessListener { documentSnapshot ->
                println("document : "+documentSnapshot)
                _liveDataJewelry.value = documentSnapshot.toJewelry()
            }
    }
}