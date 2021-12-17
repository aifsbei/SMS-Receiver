package com.tmvlg.smsreceiver.domain.numberforrent

import androidx.lifecycle.LiveData
import kotlin.Number

class GetNumberForRentListUseCase(private var numberForRentRepository: NumberForRentRepository) {

    fun getNumberForRentList(): LiveData<List<NumberForRent>> {
        return numberForRentRepository.getNumberForRentList()
    }
}