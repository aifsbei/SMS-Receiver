package com.tmvlg.smsreceiver.data.preferences

import android.content.Context
import android.content.SharedPreferences

class SettingsPreferenceProvider(context: Context) {
    private val appContext = context.applicationContext
    private val preference: SharedPreferences
        get() = appContext.getSharedPreferences(SETTINGS_PREFERENCES, 0)

    fun saveSettings(darkThemeEnabled: Boolean?, copyPrefixEnabled: Boolean?) {
        if (darkThemeEnabled != null) {
            preference.edit().putBoolean(
                KEY_DARK_THEME_ENABLED,
                darkThemeEnabled
            ).apply()
        }
        if (copyPrefixEnabled != null) {
            preference.edit().putBoolean(
                KEY_COPY_PREFIX_ENABLED,
                copyPrefixEnabled
            ).apply()
        }
    }

    fun getSettings(): Map<String, Boolean> {
        val darkThemeEnabled = preference.getBoolean(
            KEY_DARK_THEME_ENABLED,
            false
        )
        val copyPrefixEnabled = preference.getBoolean(
            KEY_COPY_PREFIX_ENABLED,
            false
        )
        return mapOf(
            KEY_DARK_THEME_ENABLED to darkThemeEnabled,
            KEY_COPY_PREFIX_ENABLED to copyPrefixEnabled
        )
    }

    companion object {
        const val SETTINGS_PREFERENCES = "settingsprefs"
        const val KEY_DARK_THEME_ENABLED = "keyDarkThemeEnabled"
        const val KEY_COPY_PREFIX_ENABLED = "keyCopyPrefixEnabled"
    }
}