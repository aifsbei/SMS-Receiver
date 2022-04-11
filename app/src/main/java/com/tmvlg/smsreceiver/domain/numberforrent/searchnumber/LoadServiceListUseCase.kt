package com.tmvlg.smsreceiver.domain.numberforrent.searchnumber

import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository
import kotlinx.coroutines.flow.Flow

class LoadServiceListUseCase(private val numberForRentRepository: NumberForRentRepository) {

    operator fun invoke(): Flow<List<Service>> {
        return numberForRentRepository.loadServiceList()
    }
}