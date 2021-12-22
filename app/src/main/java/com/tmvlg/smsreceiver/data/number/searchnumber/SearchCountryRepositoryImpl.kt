package com.tmvlg.smsreceiver.data.number.searchnumber

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tmvlg.smsreceiver.data.NumberParser
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Country
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.SearchCountryRepository
import java.lang.RuntimeException

object SearchCountryRepositoryImpl : SearchCountryRepository {

    private val countryList = mutableListOf<Country>()

    private val countryListLD = MutableLiveData<List<Country>>()

    private var autoIncrementId = 0

    override fun addCountry(country: Country) {
        if (country.id == Country.UNDEFINED_ID)
            country.id = autoIncrementId++
        countryList.add(country)
    }

    override fun getCountry(countryId: Int): Country {
        return countryList.find {
            it.id == countryId
        } ?: throw RuntimeException("Country with id = $countryId not found!")
    }

    override fun deleteCountryList() {
        countryList.clear()
        updateList()
    }

    override fun loadCountryList() {
        val parser = NumberParser()
        parser.getCountries().forEach {
            Log.d("1", "loadCountryList: $it")
            addCountry(it)
        }
        updateList()
    }

    override fun getCountryList(): LiveData<List<Country>> {
        return countryListLD
    }

    private fun updateList() {
        countryListLD.postValue(countryList.toList())
    }

    private fun clearList() {
        countryList.clear()
    }
}