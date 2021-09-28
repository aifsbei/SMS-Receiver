package com.tmvlg.smsreceiver.domain.freenumber

class DeleteFreeNumberListUseCase(private val freeNumberRepository: FreeNumberRepository) {

    fun deleteFreeNumberList() {
        freeNumberRepository.deleteFreeNumberList()
    }
}