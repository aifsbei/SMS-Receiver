package com.tmvlg.smsreceiver.domain.numberforrent

import androidx.lifecycle.LiveData
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Country

interface NumberForRentRepository {

    fun getNumberForRent(numberForRentId: Int): NumberForRent

    fun getNumberForRentList(): LiveData<List<NumberForRent>>

    fun addNumberForRent(numberForRent: NumberForRent)
}