package com.tmvlg.smsreceiver.domain.numberforrent

import androidx.lifecycle.LiveData
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Country
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Service
import kotlinx.coroutines.flow.Flow

interface NumberForRentRepository {

    fun getNumberForRent(numberForRentId: Int): NumberForRent

    fun getNumberForRentList(): LiveData<List<NumberForRent>>

    fun addNumberForRent(numberForRent: NumberForRent)

    fun loadAvailableCountries(serviceId: Int): Flow<List<Country>>

    fun getCountry(countryId: Int): Country

    fun loadServiceList(): Flow<List<Service>>
}