package com.tmvlg.smsreceiver.domain.settings

class LoadSettingsUseCase(private val settingsRepository: SettingsRepository) {
    fun loadSettings(): Map<String, Boolean> {
        return settingsRepository.loadSettings()
    }
}