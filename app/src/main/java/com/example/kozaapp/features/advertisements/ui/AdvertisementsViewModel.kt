package com.example.kozaapp.features.advertisements.ui

import androidx.lifecycle.ViewModel
import com.example.kozaapp.features.advertisements.data.AdvertisementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdvertisementsViewModel @Inject constructor(
    private val advertisementRepository: AdvertisementRepository
): ViewModel() {
    suspend fun getAds(){
        advertisementRepository.deleteAllAdvertisements()
        advertisementRepository.copyAdvertisementsFromServer()
    }
}