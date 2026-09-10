package com.flashshare.app.data.model

data class AppSettings(
    val deviceName: String = android.os.Build.MODEL,
    val downloadFolder: String = "/FlashShare",
    val autoAcceptTransfers: Boolean = false,
    val enableNotifications: Boolean = true,
    val darkMode: Boolean = false,
    val enableSound: Boolean = true
)
