package com.flashshare.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Device(
    val id: String,
    val name: String,
    val ipAddress: String,
    val port: Int,
    val isConnected: Boolean = false,
    val lastSeen: Long = System.currentTimeMillis(),
    val modelName: String = "",
    val androidVersion: String = ""
) : Parcelable
