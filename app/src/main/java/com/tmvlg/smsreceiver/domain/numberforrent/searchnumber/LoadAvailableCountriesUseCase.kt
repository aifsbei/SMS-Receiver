package com.tmvlg.smsreceiver.domain.numberforrent.searchnumber

import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository
import kotlinx.coroutines.flow.Flow

class LoadAvailableCountriesUseCase(private val numberForRentRepository: NumberForRentRepository) {

    operator fun invoke(serviceId: Int): Flow<List<Country>> {
        return numberForRentRepository.loadAvailableCountries(serviceId)
    }
}