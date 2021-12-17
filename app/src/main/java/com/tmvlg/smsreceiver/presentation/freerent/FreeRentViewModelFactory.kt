package com.tmvlg.smsreceiver.presentation.freerent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessageRepository
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumberRepository
import com.tmvlg.smsreceiver.presentation.rent.RentNumberViewModel
import java.lang.RuntimeException

class FreeRentViewModelFactory(
    private val freeNumberRepository: FreeNumberRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FreeRentViewModel::class.java))
            return FreeRentViewModel(freeNumberRepository) as T
        throw RuntimeException("Unknown view model class $modelClass")
    }

}