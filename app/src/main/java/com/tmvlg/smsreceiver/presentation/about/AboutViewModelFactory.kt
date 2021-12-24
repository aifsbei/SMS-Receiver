package com.tmvlg.smsreceiver.presentation.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tmvlg.smsreceiver.domain.settings.SettingsRepository
import com.tmvlg.smsreceiver.presentation.freerent.FreeRentViewModel
import java.lang.RuntimeException

class AboutViewModelFactory(
    private val settingsRepository: SettingsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AboutViewModel::class.java))
            return AboutViewModel(settingsRepository) as T
        throw RuntimeException("Unknown view model class $modelClass")
    }
}