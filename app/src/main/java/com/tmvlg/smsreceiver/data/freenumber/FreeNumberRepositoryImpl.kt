package com.tmvlg.smsreceiver.data.freenumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tmvlg.smsreceiver.data.FreeNumbersParser
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumberRepository
import java.lang.RuntimeException

object FreeNumberRepositoryImpl : FreeNumberRepository {

    private val freeNumberLD = MutableLiveData<List<FreeNumber>>()

    private val freeNumberList = mutableListOf<FreeNumber>()

    private var autoIncrementId = 0

//    init {
//        val parser = FreeNumbersParser()
//        for (item in parser.numbersList) {
//            addFreeNumber(item)
//        }
//    }

    override fun addFreeNumber(freeNumber: FreeNumber) {
        freeNumber.id = autoIncrementId++
        freeNumberList.add(freeNumber)
        updateList()
    }

    override fun getFreeNumberList(): LiveData<List<FreeNumber>> {
        return freeNumberLD
    }

    override fun getFreeNumber(freeNumberId: Int): FreeNumber {
        return freeNumberList.find { it.id == freeNumberId }
                ?: throw RuntimeException("Number with id = $freeNumberId not found!")
    }

    override suspend fun loadFreeNumberList() {
        val parser = FreeNumbersParser()
        parser.parse_numbers()
        for (item in parser.numbersList) {
            addFreeNumber(item)
        }
    }

    private fun updateList() {
        freeNumberLD.postValue(freeNumberList.toList())
    }

}