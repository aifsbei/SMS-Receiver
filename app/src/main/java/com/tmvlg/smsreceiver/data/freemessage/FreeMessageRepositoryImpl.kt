package com.tmvlg.smsreceiver.data.freemessage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tmvlg.smsreceiver.data.FreeNumbersParser
import com.tmvlg.smsreceiver.data.freenumber.FreeNumberRepositoryImpl
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessage
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessageRepository
import java.lang.RuntimeException

object FreeMessageRepositoryImpl : FreeMessageRepository {

    private val freeMessageLD = MutableLiveData<List<FreeMessage>>()

    private val freeMessageList = mutableListOf<FreeMessage>()

    private var autoIncrementId = 0

    init {
        val parser = FreeNumbersParser()
        for (item in parser.messagesList) {
            addFreeMessage(item)
        }
    }

    override fun addFreeMessage(freeMessage: FreeMessage) {
        freeMessage.id = autoIncrementId++
        freeMessageList.add(freeMessage)
        updateList()
    }

    override fun getFreeMessage(freeMessageId: Int): FreeMessage {
        return freeMessageList.find { it.id == freeMessageId }
                ?: throw RuntimeException("Message with id = $freeMessageId not found!")
    }

    override fun getFreeMessageList(): LiveData<List<FreeMessage>> {
        return freeMessageLD
    }

    private fun updateList() {
        freeMessageLD.postValue(freeMessageList.toList())
    }
}