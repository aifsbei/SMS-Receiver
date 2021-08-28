package com.tmvlg.smsreceiver.domain.freemessage

import androidx.lifecycle.LiveData

interface FreeMessageRepository {

    fun addFreeMessage(freeMessage: FreeMessage)

    fun getFreeMessage(freeMessageId: Int) : FreeMessage

    fun getFreeMessageList() : LiveData<List<FreeMessage>>


}