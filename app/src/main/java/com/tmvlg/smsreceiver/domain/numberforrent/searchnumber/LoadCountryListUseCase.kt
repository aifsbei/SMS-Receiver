package com.tmvlg.smsreceiver.domain.numberforrent.searchnumber

import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository

class LoadCountryListUseCase(private val searchCountryRepository: SearchCountryRepository) {

    fun loadCountryList() {
        return searchCountryRepository.loadCountryList()
    }
}