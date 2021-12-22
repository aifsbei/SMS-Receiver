package com.tmvlg.smsreceiver.domain.numberforrent.searchnumber

class GetCountryUseCase(private var countryRepository: SearchCountryRepository) {

    fun getCountry(countryId: Int): Country {
        return countryRepository.getCountry(countryId)
    }
}