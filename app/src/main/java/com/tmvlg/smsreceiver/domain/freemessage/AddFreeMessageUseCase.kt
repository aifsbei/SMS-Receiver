package com.tmvlg.smsreceiver.domain.freemessage

class AddFreeMessageUseCase(private val freeMessageRepository: FreeMessageRepository) {

    fun addFreeMessage(freeMessage: FreeMessage) {
        freeMessageRepository.addFreeMessage(freeMessage)
    }
}