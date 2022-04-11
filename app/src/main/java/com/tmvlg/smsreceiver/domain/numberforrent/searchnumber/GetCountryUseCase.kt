package com.tmvlg.smsreceiver.domain.numberforrent.searchnumber

import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository

class GetCountryUseCase(private var repository: NumberForRentRepository) {

    operator fun invoke(countryId: Int): Country {
        return repository.getCountry(countryId)
    }
}