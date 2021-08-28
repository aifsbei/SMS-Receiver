package com.tmvlg.smsreceiver.data.freenumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumberRepository
import java.lang.RuntimeException

object FreeNumberRepositoryImpl : FreeNumberRepository {

    private val freeNumberLD = MutableLiveData<List<FreeNumber>>()

    private val freeNumberList = mutableListOf<FreeNumber>()

    private var autoIncrementId = 0

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

    private fun updateList() {
        freeNumberLD.value = freeNumberList.toList()
    }
}