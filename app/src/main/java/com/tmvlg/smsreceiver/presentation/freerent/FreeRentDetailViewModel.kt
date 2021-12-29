package com.tmvlg.smsreceiver.presentation.freerent

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.tmvlg.smsreceiver.data.preferences.SettingsPreferenceProvider
import com.tmvlg.smsreceiver.domain.freemessage.DeleteFreeMessageListUseCase
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessageRepository
import com.tmvlg.smsreceiver.domain.freemessage.GetFreeMessageListUseCase
import com.tmvlg.smsreceiver.domain.freemessage.LoadFreeMessageListUseCase
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumberRepository
import com.tmvlg.smsreceiver.domain.freenumber.GetFreeNumberUseCase
import com.tmvlg.smsreceiver.domain.settings.LoadSettingsUseCase
import com.tmvlg.smsreceiver.domain.settings.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.RuntimeException

class FreeRentDetailViewModel(
    private val numberRepository: FreeNumberRepository,
    private val messageRepository: FreeMessageRepository,
    private val settingsRepository: SettingsRepository
): ViewModel() {

    private val getFreeNumberUseCase = GetFreeNumberUseCase(numberRepository)
    private val getFreeMessageListUseCase = GetFreeMessageListUseCase(messageRepository)
    private val loadFreeMessageListUseCase = LoadFreeMessageListUseCase(messageRepository)
    private val deleteListUseCase = DeleteFreeMessageListUseCase(messageRepository)
    private val loadSettingsUseCase = LoadSettingsUseCase(settingsRepository)

    var messageList = getFreeMessageListUseCase.getFreeMessageList()

    private var _getFreeNumberException = MutableLiveData<RuntimeException?>()
    val getFreeNumberException = _getFreeNumberException.map { it }

    fun initMessageRepository(freeNumberId: Int) {
        val freeNumber: FreeNumber
        try {
            freeNumber = getFreeNumberUseCase.getFreeNumber(freeNumberId)
            _getFreeNumberException.postValue(null)
        } catch (e: RuntimeException) {
            _getFreeNumberException.postValue(e)
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("1", "problem: COROUTINE STARTED")
            loadFreeMessageListUseCase.loadFreeMessageList(freeNumber)
            Log.d("1", "problem: COROUTINE ENDED")
            Log.d("1", "problem: ${messageList.value}")

        }
    }

    fun getFreeNumber(freeNumberId: Int) : FreeNumber? {
        val freeNumber: FreeNumber
        try {
            freeNumber = getFreeNumberUseCase.getFreeNumber(freeNumberId)
            _getFreeNumberException.postValue(null)
        } catch (e: RuntimeException) {
            _getFreeNumberException.postValue(e)
            return null
        }
        return freeNumber
    }

    fun clearList() {
        return deleteListUseCase.deleteFreeMessageList()
    }

    fun doCopyPrefix(): Boolean {
        val settings = loadSettingsUseCase.loadSettings()
        return settings[SettingsPreferenceProvider.KEY_COPY_PREFIX_ENABLED] ?: false
    }

    fun isDarkTheme(): Boolean {
        val settings = loadSettingsUseCase.loadSettings()
        return settings[SettingsPreferenceProvider.KEY_DARK_THEME_ENABLED] ?: false
    }

}