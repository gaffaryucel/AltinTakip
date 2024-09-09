package com.example.altntakip.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.altntakip.model.FinanceResponse
import com.example.altntakip.model.GoldInfo
import com.example.altntakip.model.JewelryModel
import com.example.altntakip.repo.ApiRepository
import com.example.altntakip.repo.FirebaseRepository
import com.example.altntakip.util.CurrencyType
import com.example.altntakip.util.Resource
import com.example.altntakip.util.toJewelry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// HomeViewModel.kt
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val goldRepository: ApiRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private var _firebaseMessage = MutableLiveData<Resource<Boolean>>()
    val firebaseMessage: LiveData<Resource<Boolean>>
        get() = _firebaseMessage

    private var _jewelryList = MutableLiveData<List<JewelryModel>>()
    val jewelryList: LiveData<List<JewelryModel>>
        get() = _jewelryList

    private val _goldPriceLiveData = MutableLiveData<List<GoldInfo>?>()
    val goldPriceLiveData: LiveData<List<GoldInfo>?> get() = _goldPriceLiveData

    val _goldLiveData = MutableLiveData<List<GoldInfo>?>()

    private val _silverPriceLiveData = MutableLiveData<GoldInfo>()
    val silverPriceLiveData: LiveData<GoldInfo> get() = _silverPriceLiveData

    private val _platinumPriceLiveData = MutableLiveData<GoldInfo>()
    val platinumPriceLiveData: LiveData<GoldInfo> get() = _platinumPriceLiveData

    private val _palladiumPriceLiveData = MutableLiveData<GoldInfo>()
    val palladiumPriceLiveData: LiveData<GoldInfo> get() = _palladiumPriceLiveData

    fun getGoldData() = viewModelScope.launch{
        val call = goldRepository.getGoldData()
        call.enqueue(object : Callback<FinanceResponse> {
            override fun onResponse(call: Call<FinanceResponse>, response: Response<FinanceResponse>) {
                if (response.isSuccessful) {
                    val financeData = response.body()
                    val list = ArrayList<GoldInfo>()
                    val gold = GoldInfo(
                        financeData?.gra?.name.toString(),
                        financeData?.gra?.buying.toString(),
                        financeData?.gra?.selling.toString(),
                        financeData?.gra?.change.toString(),
                        CurrencyType.GOLD
                    )
                    val usd = GoldInfo(
                        financeData?.usd?.name.toString(),
                        financeData?.usd?.buying.toString(),
                        financeData?.usd?.selling.toString(),
                        financeData?.usd?.change.toString(),
                        CurrencyType.USD
                    )
                    val eur = GoldInfo(
                        financeData?.eur?.name.toString(),
                        financeData?.eur?.buying.toString(),
                        financeData?.eur?.selling.toString(),
                        financeData?.eur?.change.toString(),
                        CurrencyType.EUR
                    )

                    list.add(gold)
                    list.add(usd)
                    list.add(eur)

                    _goldLiveData.value = list
                } else {
                    // Hata durumunu işleyin
                    println("Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<FinanceResponse>, t: Throwable) {
                // Hata durumunu işleyin
                println("Failure: ${t.message}")
            }
        })
    }

    fun getBestVillas(limit: Long) = viewModelScope.launch {
        _firebaseMessage.value = Resource.loading(null)
        firebaseRepository.getAllJewelryFromFirestore(limit)
            .addOnSuccessListener {
                println("it : "+it)
                val villaList = mutableListOf<JewelryModel>()
                for (document in it.documents) {
                    println("doc : "+document)
                    // Belgeden her bir videoyu çek
                    document.toJewelry()?.let { villa ->
                        villaList.add(villa)
                        println("villa : "+villa)
                    }
                }
                _jewelryList.value = villaList
                _firebaseMessage.value = Resource.success(null)
            }.addOnFailureListener { exception ->
                // Hata durzumunda işlemleri buraya ekleyebilirsiniz
                _firebaseMessage.value = Resource.error("Belge alınamadı. Hata: $exception", null)
            }
    }



}
