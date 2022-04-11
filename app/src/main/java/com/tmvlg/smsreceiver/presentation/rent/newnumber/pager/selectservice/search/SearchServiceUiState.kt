package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice.search

import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Service

sealed class SearchServiceUiState {
    object Idle : SearchServiceUiState()

    object Progress : SearchServiceUiState()

    data class Success(val serviceList: List<Service>) : SearchServiceUiState()

    data class Error(val t: Throwable) : SearchServiceUiState()
}