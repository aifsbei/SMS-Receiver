package com.tmvlg.smsreceiver.data.freenumber

import android.util.Log
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

    override fun addFreeNumber(freeNumber: FreeNumber) {
        if (freeNumber.id == FreeNumber.UNDEFINED_ID)
            freeNumber.id = autoIncrementId++
        freeNumberList.add(freeNumber)
        updateList()
    }

    override fun deleteFreeNumberList() {
//        Log.d("1", "task: delete")
        clearList()
        updateList()
    }

    override fun getFreeNumberList(): LiveData<List<FreeNumber>> {
//        Log.d("1", "task: get")
        return freeNumberLD
    }

    override fun getFreeNumber(freeNumberId: Int): FreeNumber {
        return freeNumberList.find { it.id == freeNumberId }
                ?: throw RuntimeException("Number with id = $freeNumberId not found!")
    }

    override fun filterFreeNumberList(query: String) {
        val tempList = freeNumberList.toList().filter {
            it.counrty_name.lowercase().contains(query.lowercase()) ||
                    it.country_code.lowercase().contains(query.lowercase()) ||
                    it.call_number.lowercase().contains(query.lowercase()) ||
                    it.call_number_prefix.lowercase().contains(query.lowercase())
        }
        freeNumberLD.postValue(tempList.toList())
    }

    override suspend fun loadFreeNumberList() {
//        Log.d("1", "task: load")
        clearList()
        val parser = FreeNumbersParser()
        val numbers = parser.parse_numbers()
        for (item in numbers) {
            addFreeNumber(item)
        }
    }

    private fun updateList() {
        freeNumberLD.postValue(freeNumberList.toList())
    }

    private fun clearList() {
        freeNumberList.clear()
    }

}