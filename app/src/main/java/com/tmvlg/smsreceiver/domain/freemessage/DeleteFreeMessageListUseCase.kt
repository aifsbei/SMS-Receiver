package com.tmvlg.smsreceiver.domain.freemessage

class DeleteFreeMessageListUseCase(private val freeMessageRepository: FreeMessageRepository) {

    fun deleteFreeMessageList() {
        freeMessageRepository.deleteFreeMessageList()
    }
}