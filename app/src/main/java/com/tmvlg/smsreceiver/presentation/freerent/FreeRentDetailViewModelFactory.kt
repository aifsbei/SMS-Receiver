package com.tmvlg.smsreceiver.presentation.freerent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessageRepository
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumberRepository
import com.tmvlg.smsreceiver.domain.settings.SettingsRepository
import java.lang.RuntimeException

class FreeRentDetailViewModelFactory(
    private val numberRepository: FreeNumberRepository,
    private val messageRepository: FreeMessageRepository,
    private val settingsRepository: SettingsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FreeRentDetailViewModel::class.java))
            return FreeRentDetailViewModel(numberRepository, messageRepository, settingsRepository) as T
        throw RuntimeException("Unknown view model class $modelClass")
    }
}