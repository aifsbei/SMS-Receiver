package com.tmvlg.smsreceiver.domain.freenumber

class AddFreeNumberUseCase(private val freeNumberRepository: FreeNumberRepository) {

    fun addFreeNumber(freeNumber: FreeNumber) {
        freeNumberRepository.addFreeNumber(freeNumber)
    }
}