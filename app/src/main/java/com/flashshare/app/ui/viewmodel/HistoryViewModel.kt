package com.flashshare.app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flashshare.app.data.model.TransferRecord
import com.flashshare.app.data.repository.SettingsRepository
import com.flashshare.app.data.repository.TransferHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val transferHistoryRepository: TransferHistoryRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val transferHistory: StateFlow<List<TransferRecord>> = transferHistoryRepository.getAllTransfers()
    val appSettings: StateFlow<com.flashshare.app.data.model.AppSettings> = settingsRepository.appSettings

    fun clearAllHistory() {
        viewModelScope.launch {
            transferHistoryRepository.clearAllHistory()
        }
    }

    fun clearOldRecords(beforeTime: Long) {
        viewModelScope.launch {
            transferHistoryRepository.clearOldRecords(beforeTime)
        }
    }
}
