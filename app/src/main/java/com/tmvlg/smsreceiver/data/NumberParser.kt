package com.tmvlg.smsreceiver.data

import com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.OnlineSimApi
import com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.models.CountryModel
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Country
import com.tmvlg.smsreceiver.util.formatCountryName
import com.tmvlg.smsreceiver.util.getCountryCodeByName
import com.tmvlg.smsreceiver.util.getCountryCodeByPrefix
import retrofit2.Response
import java.lang.RuntimeException

class NumberParser {

    fun getCountries(): List<Country> {
        val countryList = mutableListOf<Country>()
        val countriesResponse: Response<Map<String, CountryModel>> = OnlineSimApi
            .retrofitService
            .getCountries()
            .execute() ?: throw RuntimeException()
        val countriesBody = countriesResponse.body() ?: throw RuntimeException()
        countriesBody.entries.forEach { countryItem ->

            val countryName = formatCountryName(countryItem.value.name)

            val country = Country(
                name = countryName,
                code = countryItem.value.code,
                new = countryItem.value.new,
                enabled = countryItem.value.enabled
            )
            var numbersCount = 0
            countryItem.value.services.forEach { serviceItem ->
                numbersCount = if (serviceItem.value.count > numbersCount)
                    serviceItem.value.count
                else
                    numbersCount
            }
            country.totalNumbers = numbersCount
//            val countryCode = getCountryCodeByName(countryName) ?: ""     //WORKS BAD
            var countryCode = getCountryCodeByPrefix(countryItem.key.toInt()) ?: return@forEach
            if (countryCode == UNDEFINED_COUNTRY_CODE) {
                countryCode = "undefined"
            }
            country.iconPath = "flag_${countryCode.lowercase()}"
            countryList.add(country)
        }
        return countryList
    }

    companion object {
        const val TAG = "1"
        const val UNDEFINED_COUNTRY_CODE = "ZZ"
    }
}