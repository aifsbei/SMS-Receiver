package com.tmvlg.smsreceiver.domain.freenumber

import androidx.lifecycle.LiveData
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumberRepository
import com.tmvlg.smsreceiver.domain.freenumber.GetFreeNumberListUseCase
import com.tmvlg.smsreceiver.domain.freenumber.LoadFreeNumberListUseCase

class FilterFreeNumberListUseCase(private val freeNumberRepository: FreeNumberRepository) {

    fun filterFreeNumberListUseCase(query: String) {
        freeNumberRepository.filterFreeNumberListUseCase(query)
    }
}