package com.tmvlg.smsreceiver

import com.tmvlg.smsreceiver.util.getCountryCodeByPrefix
import org.junit.Test

class CountryCodes {

    @Test
    fun countryCodeByPrefix() {
        println(getCountryCodeByPrefix(1))
    }
}