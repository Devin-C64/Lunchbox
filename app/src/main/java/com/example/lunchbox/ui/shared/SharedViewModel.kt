package com.example.lunchbox.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val _showCompletedOffer = MutableLiveData<Boolean>()
    val showCompletedOffer: LiveData<Boolean> get() = _showCompletedOffer


    private val _showTextOffersCompleted = MutableLiveData<Boolean>()
    val showTextOffersCompleted: LiveData<Boolean> get() = _showTextOffersCompleted


    fun setCompletedOfferVisibility(visible: Boolean) {
        _showCompletedOffer.value = visible
    }

    fun setTextOffersCompletedVisibility(visible: Boolean) {
        _showTextOffersCompleted.value = visible
    }
}
