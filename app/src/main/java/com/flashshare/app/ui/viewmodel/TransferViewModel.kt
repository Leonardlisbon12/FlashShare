package com.flashshare.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flashshare.app.data.model.SharedFile
import com.flashshare.app.data.model.TransferProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class TransferViewModel @Inject constructor() : ViewModel() {
    private val _selectedFiles = MutableStateFlow<List<SharedFile>>(emptyList())
    val selectedFiles: StateFlow<List<SharedFile>> = _selectedFiles

    private val _totalSize = MutableStateFlow(0L)
    val totalSize: StateFlow<Long> = _totalSize

    private val _transferProgress = MutableStateFlow<TransferProgress?>(null)
    val transferProgress: StateFlow<TransferProgress?> = _transferProgress

    private val _isTransferring = MutableStateFlow(false)
    val isTransferring: StateFlow<Boolean> = _isTransferring

    private val _transferError = MutableStateFlow<String?>(null)
    val transferError: StateFlow<String?> = _transferError

    fun addFiles(files: List<SharedFile>) {
        viewModelScope.launch {
            val current = _selectedFiles.value.toMutableList()
            current.addAll(files)
            _selectedFiles.value = current
            updateTotalSize()
        }
    }

    fun removeFile(file: SharedFile) {
        viewModelScope.launch {
            val current = _selectedFiles.value.toMutableList()
            current.remove(file)
            _selectedFiles.value = current
            updateTotalSize()
        }
    }

    fun clearFiles() {
        viewModelScope.launch {
            _selectedFiles.value = emptyList()
            _totalSize.value = 0L
        }
    }

    private fun updateTotalSize() {
        _totalSize.value = _selectedFiles.value.sumOf { it.size }
    }

    fun updateProgress(
        currentFileIndex: Int,
        totalFiles: Int,
        currentFileName: String,
        bytesTransferred: Long,
        totalBytes: Long,
        transferSpeed: Double,
        estimatedTimeRemaining: Long
    ) {
        val percentage = ((bytesTransferred.toDouble() / totalBytes.toDouble()) * 100).roundToInt()
        _transferProgress.value = TransferProgress(
            currentFileIndex = currentFileIndex,
            totalFiles = totalFiles,
            currentFileName = currentFileName,
            bytesTransferred = bytesTransferred,
            totalBytes = totalBytes,
            transferSpeed = transferSpeed,
            estimatedTimeRemaining = estimatedTimeRemaining,
            percentage = percentage
        )
    }

    fun startTransfer() {
        _isTransferring.value = true
        _transferError.value = null
    }

    fun completeTransfer() {
        _isTransferring.value = false
    }

    fun cancelTransfer() {
        _isTransferring.value = false
    }

    fun setError(error: String?) {
        _transferError.value = error
    }
}
