package com.example.altntakip.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.altntakip.model.JewelryModel
import com.example.altntakip.repo.ApiRepository
import com.example.altntakip.repo.FirebaseRepository
import com.example.altntakip.util.Resource
import com.google.firebase.storage.FirebaseStorage
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

    private var _firebaseMessage = MutableLiveData<Resource<Boolean>>()
    val firebaseMessage: LiveData<Resource<Boolean>>
        get() = _firebaseMessage


    fun createJewelry(jewelry : JewelryModel) = viewModelScope.launch{
        firebaseRepository.addJewelryToFirestore(UUID.randomUUID().toString(),jewelry).addOnCompleteListener {
            println("Completed")
            if (it.isSuccessful){
                _firebaseMessage.value = Resource.success()
            }else{
                _firebaseMessage.value = Resource.error("Ürün Yüklenemedi")
            }
        }
    }

    fun uploadJewelryImages(
        jewelry : JewelryModel,
        coverImage : Uri,
        imageUris: List<Uri>,
        jewelryId: String,
    )  = viewModelScope.launch{
        _firebaseMessage.value = Resource.loading()

        val newUriList = ArrayList<Uri>()
        newUriList.add(coverImage)
        newUriList.addAll(imageUris)

        val storageRef = FirebaseStorage.getInstance().reference.child("jewelry_images/$jewelryId/")
        val imageUrls = mutableListOf<String>()
        var uploadCount = 0

        // Her resim URI'sini Storage'a yükle
        newUriList.forEachIndexed { index, uri ->
            val fileName = "image_${System.currentTimeMillis()}_${index}.jpg"
            val fileRef = storageRef.child(fileName)

            fileRef.putFile(uri)
                .addOnSuccessListener { task ->
                    task.storage.downloadUrl.addOnSuccessListener { uri ->
                        imageUrls.add(uri.toString())
                        uploadCount++

                        // Tüm resimler yüklendiğinde başarıyı bildir
                        if (uploadCount == imageUris.size) {
                            jewelry.imageUrl = imageUrls[0]
                            jewelry.jewelryImages = imageUrls
                            createJewelry(jewelry)
                        }
                    }.addOnFailureListener { error ->
                        _firebaseMessage.value = Resource.error("Resimler Yüklenemedi")
                    }
                }
                .addOnFailureListener { error ->
                    _firebaseMessage.value = Resource.error("Resimler Yüklenemedi")
                }
        }
    }

}