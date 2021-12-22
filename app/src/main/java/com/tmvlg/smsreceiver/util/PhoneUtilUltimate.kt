package com.tmvlg.smsreceiver.util

import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.*

fun getCountryCodeByNumber(fullNumber: String): String {
    val phoneUtil = PhoneNumberUtil.getInstance()
    var country_code: String = ""
    try {
        // phone must begin with '+'
        val numberProto = phoneUtil.parse(fullNumber, "")
        country_code = phoneUtil.getRegionCodeForCountryCode(numberProto.countryCode)
    } catch (e: Exception) {
        System.err.println("NumberParseException was thrown: $e")
    }
    return country_code
}

fun getPrefixByCountryCode(countryCode: String): String? {
    val result = PhoneNumberUtil.getInstance().getCountryCodeForRegion(countryCode)
    if (result == 0)
        return null
    return "+${result}"
}


fun getCountryCodeByPrefix(prefix: Int): String? {
    return PhoneNumberUtil.getInstance().getRegionCodeForCountryCode(prefix)
}

fun getCountryNameByCode(countryCode: String): String? {
    val loc = Locale("en_US", countryCode)
    return loc.getDisplayCountry(Locale.ENGLISH)
}

fun getCountryCodeByName(countryName: String): String? {
    val countryCode = Locale.getISOCountries()
        .find { Locale("", it).getDisplayCountry(Locale.ENGLISH) == countryName }
    return countryCode
}

fun formatCountryName(countryName: String): String {
    var newCountryName = ""
    countryName.forEachIndexed { index, c ->
        if (index == 0) {
            newCountryName += c.uppercaseChar()
        }
        else {
            if (c == '_') {
                newCountryName += " "
            }
            else {
                newCountryName += c
            }
        }
    }
    return newCountryName
}

