package com.tmvlg.smsreceiver.domain.freenumber

class GetFreeNumberUseCase(private val freeNumberRepository: FreeNumberRepository) {

    fun getFreeNumber(freeNumberId: Int) : FreeNumber {
        return freeNumberRepository.getFreeNumber(freeNumberId)
    }
}