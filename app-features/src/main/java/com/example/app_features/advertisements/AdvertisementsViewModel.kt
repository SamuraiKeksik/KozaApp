//package com.example.advertisements
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.kozaapp.features.advertisements.data.AdvertisementRepository
//import com.example.kozaapp.features.advertisements.data.model.Advertisement
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class AdvertisementsViewModel @Inject constructor(
//    private val advertisementRepository: AdvertisementRepository
//): ViewModel() {
//
//    init{
//        viewModelScope.launch {
//            getAds()
//        }
//    }
//
//    val advertisementsUiState: StateFlow<AdvertisementsUiState> =
//        advertisementRepository.getAllAdvertisementsStream().map {
//            AdvertisementsUiState(it)
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//            initialValue = AdvertisementsUiState()
//        )
//    companion object {
//        private const val TIMEOUT_MILLIS = 5_000L
//    }
//    suspend fun getAds(){
//        advertisementRepository.deleteAllAdvertisements()
//        advertisementRepository.copyAdvertisementsFromServer()
//    }
//}
//
//data class AdvertisementsUiState(val advertisementsList: List<Advertisement> = listOf())