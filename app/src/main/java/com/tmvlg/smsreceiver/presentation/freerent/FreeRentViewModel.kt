package com.tmvlg.smsreceiver.presentation.freerent

import android.app.Application
import android.os.AsyncTask
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.backend.RecyclerItemDecoration
import com.tmvlg.smsreceiver.data.freenumber.FreeNumberRepositoryImpl
import com.tmvlg.smsreceiver.domain.FreeNumber.FilterFreeNumberListUseCase
import com.tmvlg.smsreceiver.domain.freenumber.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.HashMap

class FreeRentViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application

    private var repository:  FreeNumberRepository = FreeNumberRepositoryImpl

    private val addFreeNumberUseCase = AddFreeNumberUseCase(repository)
    private val getFreeNumberUseCase = GetFreeNumberUseCase(repository)
    private val getFreeNumberListUseCase = GetFreeNumberListUseCase(repository)
    private val loadFreeNumberListUseCase = LoadFreeNumberListUseCase(repository)
    private val filterFreeNumberListUseCase = FilterFreeNumberListUseCase(repository)

    var numberList = getFreeNumberListUseCase.getFreeNumberList()

    fun initRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            loadFreeNumberListUseCase.loadFreeNumberList()
        }
    }

    fun filterFreeNumberList(query: String) {
        filterFreeNumberListUseCase.filterFreeNumberListUseCase(query)
    }

}