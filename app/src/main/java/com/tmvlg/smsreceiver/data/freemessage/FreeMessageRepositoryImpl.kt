package com.tmvlg.smsreceiver.data.freemessage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tmvlg.smsreceiver.data.FreeNumbersParser
import com.tmvlg.smsreceiver.data.freenumber.FreeNumberRepositoryImpl
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessage
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessageRepository
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import java.lang.RuntimeException

object FreeMessageRepositoryImpl : FreeMessageRepository {

    private val freeMessageLD = MutableLiveData<List<FreeMessage>>()

    private val freeMessageList = mutableListOf<FreeMessage>()

    private var autoIncrementId = 0

//    init {
//        val parser = FreeNumbersParser()
//        for (item in parser.messagesList) {
//            addFreeMessage(item)
//        }
//    }

    override fun addFreeMessage(freeMessage: FreeMessage) {
        if (freeMessage.id == FreeMessage.UNDEFINED_ID)
            freeMessage.id = autoIncrementId++
        freeMessageList.add(freeMessage)
        Log.d("1", "addFreeMessage: ${freeMessage.message_title} added")
        updateList()
    }

    override fun getFreeMessage(freeMessageId: Int): FreeMessage {
        return freeMessageList.find { it.id == freeMessageId }
                ?: throw RuntimeException("Message with id = $freeMessageId not found!")
    }

    override fun getFreeMessageList(): LiveData<List<FreeMessage>> {
        Log.d("1", "problem: get")
        return freeMessageLD
    }

    override fun deleteFreeMessageList() {
        clearList()
        updateList()
    }

    override suspend fun loadFreeMessageList(freeNumber: FreeNumber) {
        Log.d("1", "loadFreeMessageList: another load")
        clearList()
        val parser = FreeNumbersParser()
        val messages = parser.parse_messages(freeNumber)
        for (item in messages) {
            addFreeMessage(item)
        }
    }

    private fun updateList() {
        freeMessageLD.postValue(freeMessageList.toList())
        Log.d("1", "updateList: ${freeMessageList.size}")
    }

    private fun clearList() {
        freeMessageList.clear()
    }

}