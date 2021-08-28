package com.tmvlg.smsreceiver.domain.freemessage

class GetFreeMessageUseCase(private val freeMessageRepository: FreeMessageRepository) {

    fun getFreeMessage(freeMessageId: Int) : FreeMessage {
        return freeMessageRepository.getFreeMessage(freeMessageId)
    }
}