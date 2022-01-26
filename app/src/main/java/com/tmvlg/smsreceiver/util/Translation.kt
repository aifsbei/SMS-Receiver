package com.tmvlg.smsreceiver.util

import java.util.*

fun String.translateCountryName(languageRegion: String): String {
    val language = Locale.Builder().setLanguage(languageRegion).build()
    val country = Locale.Builder().setRegion(this).build()
    return country.getDisplayCountry(language)
}

fun String.translateCountryName(): String {
    val language = Locale.getDefault()
    val country = Locale.Builder().setRegion(this).build()
    return country.getDisplayCountry(language)
}

