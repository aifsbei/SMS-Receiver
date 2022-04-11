package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.LoadServiceListUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchServiceViewModel(
    private val repository: NumberForRentRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<SearchServiceUiState>(SearchServiceUiState.Idle)
    val uiState: StateFlow<SearchServiceUiState> = _uiState.asStateFlow()

    private val loadServiceListUseCase = LoadServiceListUseCase(repository)

    fun loadCountries() = viewModelScope.launch {
        loadServiceListUseCase()
            .catch { throwable ->
                _uiState.value = SearchServiceUiState.Error(throwable)
            }
            .collect { serviceList ->
                _uiState.value = SearchServiceUiState.Success(serviceList)
            }
    }
}