package com.flashshare.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flashshare.app.data.model.AppSettings
import com.flashshare.app.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val appSettings: StateFlow<AppSettings> = settingsRepository.appSettings

    fun updateDeviceName(name: String) {
        viewModelScope.launch {
            settingsRepository.updateDeviceName(name)
        }
    }

    fun updateDownloadFolder(folder: String) {
        viewModelScope.launch {
            settingsRepository.updateDownloadFolder(folder)
        }
    }

    fun updateAutoAccept(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateAutoAccept(enabled)
        }
    }

    fun updateNotifications(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateNotifications(enabled)
        }
    }

    fun updateDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateDarkMode(enabled)
        }
    }

    fun updateSound(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateSound(enabled)
        }
    }
}
