package com.tmvlg.smsreceiver.domain.freenumber

class LoadFreeNumberListUseCase(private val freeNumberRepository: FreeNumberRepository) {

    suspend fun loadFreeNumberList() {
        freeNumberRepository.loadFreeNumberList()
    }
}