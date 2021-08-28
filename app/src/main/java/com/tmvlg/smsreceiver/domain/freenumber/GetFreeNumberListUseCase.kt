package com.tmvlg.smsreceiver.domain.freenumber

import androidx.lifecycle.LiveData

class GetFreeNumberListUseCase(private val freeNumberRepository: FreeNumberRepository) {

    fun getFreeNumberList() : LiveData<List<FreeNumber>> {
        return freeNumberRepository.getFreeNumberList()
    }
}