package com.tmvlg.smsreceiver.presentation.rent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository
import java.lang.RuntimeException

class RentNumberViewModelFactory(
    private val repository: NumberForRentRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RentNumberViewModel::class.java))
            return RentNumberViewModel(repository) as T
        throw RuntimeException("Unknown view model class $modelClass")
    }
}