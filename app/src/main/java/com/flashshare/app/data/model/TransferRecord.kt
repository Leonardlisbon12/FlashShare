package com.flashshare.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transfer_records")
data class TransferRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val transferId: String,
    val deviceName: String,
    val deviceId: String,
    val fileName: String,
    val fileCount: Int,
    val totalSize: Long,
    val transferType: String, // "send" or "receive"
    val status: String, // "completed", "failed", "cancelled"
    val speed: Double, // MB/s
    val duration: Long, // milliseconds
    val timestamp: Long = System.currentTimeMillis()
)
