package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search

import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Country

sealed class SearchCountryUiState {
    object Idle : SearchCountryUiState()

    object Progress : SearchCountryUiState()

    data class Success(
        val countryList: List<Country>
    ) : SearchCountryUiState()

    data class Error(
        val t: Throwable
    ) : SearchCountryUiState()
}