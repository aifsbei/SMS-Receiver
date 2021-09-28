package com.tmvlg.smsreceiver.domain.freemessage

import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber

class LoadFreeMessageListUseCase(private val freeMessageRepository: FreeMessageRepository) {

    suspend fun loadFreeMessageList(freeNumber: FreeNumber) {
        freeMessageRepository.loadFreeMessageList(freeNumber)
    }
}