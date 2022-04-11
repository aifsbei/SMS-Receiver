package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.LoadAvailableCountriesUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchCountryViewModel(
    private val repository: NumberForRentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchCountryUiState>(SearchCountryUiState.Idle)
    val uiState: StateFlow<SearchCountryUiState> = _uiState.asStateFlow()

    private val loadCountryListUseCase = LoadAvailableCountriesUseCase(repository)

    fun loadCountries() = viewModelScope.launch {
        loadCountryListUseCase(1)
            .catch { throwable ->
                _uiState.value = SearchCountryUiState.Error(throwable)
            }
            .collect { countryList ->
                _uiState.value = SearchCountryUiState.Success(countryList)
            }
    }
}