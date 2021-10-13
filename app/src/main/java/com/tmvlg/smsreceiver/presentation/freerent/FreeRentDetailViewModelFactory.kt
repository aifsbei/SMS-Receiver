package com.tmvlg.smsreceiver.presentation.freerent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessageRepository
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumberRepository
import java.lang.RuntimeException

class FreeRentDetailViewModelFactory(
    private val numberRepository: FreeNumberRepository,
    private val messageRepository: FreeMessageRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FreeRentDetailViewModel::class.java))
            return FreeRentDetailViewModel(numberRepository, messageRepository) as T
        throw RuntimeException("Unknown view model class $modelClass")
    }
}