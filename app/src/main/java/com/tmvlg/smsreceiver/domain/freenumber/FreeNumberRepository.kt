package com.tmvlg.smsreceiver.domain.freenumber

import androidx.lifecycle.LiveData

interface FreeNumberRepository {

    fun addFreeNumber(freeNumber: FreeNumber)

    fun getFreeNumberList() : LiveData<List<FreeNumber>>

    fun getFreeNumber(freeNumberId: Int) : FreeNumber

    fun filterFreeNumberListUseCase(query: String)

    fun deleteFreeNumberList()

    suspend fun loadFreeNumberList() : Unit

}