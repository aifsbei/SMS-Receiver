package com.tmvlg.smsreceiver.domain.numberforrent

import androidx.lifecycle.LiveData
import kotlin.Number

interface NumberForRentRepository {

    fun getNumberForRent(numberForRentId: Int): NumberForRent

    fun getNumberForRentList(): LiveData<List<NumberForRent>>

    fun addNumberForRent(numberForRent: NumberForRent)
}