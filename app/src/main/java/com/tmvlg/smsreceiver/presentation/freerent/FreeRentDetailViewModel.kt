package com.tmvlg.smsreceiver.presentation.freerent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmvlg.smsreceiver.domain.freemessage.DeleteFreeMessageListUseCase
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessageRepository
import com.tmvlg.smsreceiver.domain.freemessage.GetFreeMessageListUseCase
import com.tmvlg.smsreceiver.domain.freemessage.LoadFreeMessageListUseCase
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumberRepository
import com.tmvlg.smsreceiver.domain.freenumber.GetFreeNumberUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FreeRentDetailViewModel(
    private val numberRepository: FreeNumberRepository,
    private val messageRepository: FreeMessageRepository
): ViewModel() {

    private val getFreeNumberUseCase = GetFreeNumberUseCase(numberRepository)
    private val getFreeMessageListUseCase = GetFreeMessageListUseCase(messageRepository)
    private val loadFreeMessageListUseCase = LoadFreeMessageListUseCase(messageRepository)
    private val deleteListUseCase = DeleteFreeMessageListUseCase(messageRepository)

    var messageList = getFreeMessageListUseCase.getFreeMessageList()

    fun initMessageRepository(freeNumberId: Int) {
        val freeNumber = getFreeNumberUseCase.getFreeNumber(freeNumberId)
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("1", "problem: COROUTINE STARTED")
            loadFreeMessageListUseCase.loadFreeMessageList(freeNumber)
            Log.d("1", "problem: COROUTINE ENDED")
            Log.d("1", "problem: ${messageList.value}")

        }
    }

    fun getFreeNumber(freeNumberId: Int) : FreeNumber{
        return getFreeNumberUseCase.getFreeNumber(freeNumberId)
    }

    fun clearList() {
        return deleteListUseCase.deleteFreeMessageList()
    }

}