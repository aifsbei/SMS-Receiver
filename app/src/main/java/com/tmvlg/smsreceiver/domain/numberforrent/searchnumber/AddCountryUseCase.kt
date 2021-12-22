package com.tmvlg.smsreceiver.domain.numberforrent.searchnumber

class AddCountryUseCase(private var countryRepository: SearchCountryRepository) {

    fun addCountry(country: Country) {
        return countryRepository.addCountry(country)
    }
}