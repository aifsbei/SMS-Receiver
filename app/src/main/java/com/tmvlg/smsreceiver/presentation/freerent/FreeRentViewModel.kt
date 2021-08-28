package com.tmvlg.smsreceiver.presentation.freerent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tmvlg.smsreceiver.data.freenumber.FreeNumberRepositoryImpl
import com.tmvlg.smsreceiver.domain.freenumber.AddFreeNumberUseCase
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import com.tmvlg.smsreceiver.domain.freenumber.GetFreeNumberListUseCase
import com.tmvlg.smsreceiver.domain.freenumber.GetFreeNumberUseCase

class FreeRentViewModel : ViewModel() {

    private val repository = FreeNumberRepositoryImpl

    private val addFreeNumber = AddFreeNumberUseCase(repository)
    private val getFreeNumber = GetFreeNumberUseCase(repository)
    private val getFreeNumberList = GetFreeNumberListUseCase(repository)

    val numberList = getFreeNumberList.getFreeNumberList()

}