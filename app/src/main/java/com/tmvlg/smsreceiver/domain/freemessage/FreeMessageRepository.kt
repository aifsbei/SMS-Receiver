package com.tmvlg.smsreceiver.domain.freemessage

import androidx.lifecycle.LiveData
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber

interface FreeMessageRepository {

    fun addFreeMessage(freeMessage: FreeMessage)

    fun getFreeMessage(freeMessageId: Int) : FreeMessage

    fun getFreeMessageList() : LiveData<List<FreeMessage>>

    fun deleteFreeMessageList()

    suspend fun loadFreeMessageList(freeNumber: FreeNumber)


}