package com.flashshare.app.data.model

data class TransferProgress(
    val currentFileIndex: Int,
    val totalFiles: Int,
    val currentFileName: String,
    val bytesTransferred: Long,
    val totalBytes: Long,
    val transferSpeed: Double, // bytes per second
    val estimatedTimeRemaining: Long, // milliseconds
    val percentage: Int
)
