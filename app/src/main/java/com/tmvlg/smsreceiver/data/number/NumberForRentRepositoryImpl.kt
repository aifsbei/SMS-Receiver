package com.tmvlg.smsreceiver.data.number

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tmvlg.smsreceiver.data.NumberDataSource
import com.tmvlg.smsreceiver.data.db.NumberForRentDao
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRent
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Country
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Service
import kotlinx.coroutines.flow.Flow
import java.lang.RuntimeException

class NumberForRentRepositoryImpl(
    private val numberForRentDao: NumberForRentDao,
    private val numberDataSource: NumberDataSource
) : NumberForRentRepository {


    private val numberForRentListLD = MutableLiveData<List<NumberForRent>>()

    private val numberForRentList = mutableListOf<NumberForRent>()

    private var autoIncrementId = 0

    override fun addNumberForRent(numberForRent: NumberForRent) {
        if (numberForRent.id == FreeNumber.UNDEFINED_ID)
            numberForRent.id = autoIncrementId++
        numberForRentList.add(numberForRent)
        updateList()
    }

    override fun loadAvailableCountries(serviceId: Int): Flow<List<Country>> {
        return numberDataSource.fetchAvailableCountries(serviceId)
    }

    override fun getCountry(countryId: Int): Country {
        return Country()
    }

    override fun loadServiceList(): Flow<List<Service>> {
        return numberDataSource.fetchServiceList()
    }

    override fun getNumberForRent(numberForRentId: Int): NumberForRent {
        return numberForRentList.find { it.id == numberForRentId }
            ?: throw RuntimeException("Number with id = $numberForRentId not found!")
    }

    override fun getNumberForRentList(): LiveData<List<NumberForRent>> {
        return numberForRentListLD
    }

    private fun updateList() {
        numberForRentListLD.postValue(numberForRentList.toList())
    }

    private fun clearList() {
        numberForRentList.clear()
    }

}