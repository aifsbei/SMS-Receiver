package com.tmvlg.smsreceiver.domain.settings

class SaveSettingsUseCase(private val settingsRepository: SettingsRepository) {

    fun saveSettings(darkThemeEnabled: Boolean?, copyPrefixEnabled: Boolean?) {
        settingsRepository.saveSettings(darkThemeEnabled, copyPrefixEnabled)
    }
}