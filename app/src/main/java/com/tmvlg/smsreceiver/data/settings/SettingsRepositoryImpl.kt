package com.tmvlg.smsreceiver.data.settings

import com.tmvlg.smsreceiver.data.preferences.SettingsPreferenceProvider
import com.tmvlg.smsreceiver.domain.settings.SettingsRepository

class SettingsRepositoryImpl(
    private val preferenceProvider: SettingsPreferenceProvider
): SettingsRepository {
    override fun saveSettings(darkThemeEnabled: Boolean?, copyPrefixEnabled: Boolean?) {
        preferenceProvider.saveSettings(darkThemeEnabled, copyPrefixEnabled)
    }

    override fun loadSettings(): Map<String, Boolean> {
        return preferenceProvider.getSettings()
    }
}