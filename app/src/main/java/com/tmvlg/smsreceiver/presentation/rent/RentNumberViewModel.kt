package com.tmvlg.smsreceiver.presentation.rent

import androidx.lifecycle.ViewModel
import com.tmvlg.smsreceiver.domain.numberforrent.AddNumberForRentUseCase
import com.tmvlg.smsreceiver.domain.numberforrent.GetNumberForRentListUseCase
import com.tmvlg.smsreceiver.domain.numberforrent.GetNumberForRentUseCase
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository

class RentNumberViewModel(
    private val repository: NumberForRentRepository
) : ViewModel() {

    private val addNumberForRentUseCase = AddNumberForRentUseCase(repository)
    private val getNumberForRentUseCase = GetNumberForRentUseCase(repository)
    private val getNumberForRentListUseCase = GetNumberForRentListUseCase(repository)

    var numberForRentList = getNumberForRentListUseCase.getNumberForRentList()


}