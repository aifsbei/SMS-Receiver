package com.tmvlg.smsreceiver.domain.settings

interface SettingsRepository {

    fun saveSettings(darkThemeEnabled: Boolean?, copyPrefixEnabled: Boolean?)

    fun loadSettings(): Map<String, Boolean>
}