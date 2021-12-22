package com.tmvlg.smsreceiver.domain.numberforrent.searchnumber

class DeleteCountryListUseCase(private var searchCountryRepository: SearchCountryRepository) {

    fun deleteCountryList() {
        searchCountryRepository.deleteCountryList()
    }
}