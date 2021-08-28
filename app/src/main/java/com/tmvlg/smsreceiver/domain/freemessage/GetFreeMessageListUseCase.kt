package com.tmvlg.smsreceiver.domain.freemessage

import androidx.lifecycle.LiveData

class GetFreeMessageListUseCase(private val freeMessageRepository: FreeMessageRepository) {

    fun getFreeMessageList() : LiveData<List<FreeMessage>> {
        return  freeMessageRepository.getFreeMessageList()
    }
}