package com.tmvlg.smsreceiver.domain.numberforrent.searchnumber

import androidx.lifecycle.LiveData

interface SearchCountryRepository {

    fun addCountry(country: Country)

    fun getCountry(countryId: Int): Country

    fun deleteCountryList()

    fun loadCountryList()

    fun getCountryList(): LiveData<List<Country>>
}