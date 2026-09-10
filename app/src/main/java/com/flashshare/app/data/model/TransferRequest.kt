package com.flashshare.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransferRequest(
    val id: String,
    val senderDevice: Device,
    val files: List<SharedFile>,
    val totalSize: Long,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable
