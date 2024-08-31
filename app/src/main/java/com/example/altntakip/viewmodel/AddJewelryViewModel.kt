package com.example.altntakip.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.altntakip.model.JewelryModel
import com.example.altntakip.repo.ApiRepository
import com.example.altntakip.repo.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


// HomeViewModel.kt
@HiltViewModel
class AddJewelryViewModel @Inject constructor(
    private val goldRepository: ApiRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    fun addFakeJewelry(jewelry : JewelryModel) = viewModelScope.launch{
        firebaseRepository.addJewelryToFirestore(UUID.randomUUID().toString(),jewelry).addOnCompleteListener {
            println("Completed")
            if (it.isSuccessful){
                println("success")
            }else{
                println("fail")
            }
        }
    }

}