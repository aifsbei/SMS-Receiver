package com.tmvlg.smsreceiver.presentation.about

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.tmvlg.smsreceiver.data.preferences.SettingsPreferenceProvider
import com.tmvlg.smsreceiver.domain.settings.LoadSettingsUseCase
import com.tmvlg.smsreceiver.domain.settings.SaveSettingsUseCase
import com.tmvlg.smsreceiver.domain.settings.SettingsRepository

class AboutViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private var _darkThemeSelected = MutableLiveData<Boolean>()
    val darkThemeSelected = _darkThemeSelected.map { it }

    private var _copySettingsSelected = MutableLiveData<Boolean>()
    val copySettingsSelected = _copySettingsSelected.map { it }

    private val loadSettingsUseCase = LoadSettingsUseCase(settingsRepository)
    private val saveSettingsUseCase = SaveSettingsUseCase(settingsRepository)

    fun saveDarkThemeSelected(selected: Boolean) {
        saveSettingsUseCase.saveSettings(selected, null)
    }

    fun saveCopySettingsSelected(selected: Boolean) {
        saveSettingsUseCase.saveSettings(null, selected)
    }

    fun loadSettings(){
        val settings = loadSettingsUseCase.loadSettings()

        _darkThemeSelected.postValue(
            settings[SettingsPreferenceProvider.KEY_DARK_THEME_ENABLED]
        )
        _copySettingsSelected.postValue(
            settings[SettingsPreferenceProvider.KEY_COPY_PREFIX_ENABLED]
        )
    }

}