package com.flashshare.app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flashshare.app.data.model.Device
import com.flashshare.app.network.NearbyConnectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {
    private lateinit var connectionManager: NearbyConnectionManager
    
    val discoveredDevices: StateFlow<List<Device>>
        get() = connectionManager.discoveredDevices
    
    val isDiscovering: StateFlow<Boolean>
        get() = if (::connectionManager.isInitialized) {
            connectionManager.discoveredDevices.let { StateFlow { true } }
        } else {
            StateFlow { false }
        }

    fun initializeConnectionManager(deviceName: String) {
        connectionManager = NearbyConnectionManager(context, deviceName)
    }

    fun startDiscovery() {
        if (::connectionManager.isInitialized) {
            viewModelScope.launch {
                connectionManager.startDiscovering()
            }
        }
    }

    fun stopDiscovery() {
        if (::connectionManager.isInitialized) {
            connectionManager.stopDiscovering()
        }
    }

    fun connectToDevice(device: Device) {
        if (::connectionManager.isInitialized) {
            connectionManager.connectToDevice(device.id)
        }
    }

    fun getConnectionManager(): NearbyConnectionManager = connectionManager
}
