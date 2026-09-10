package com.flashshare.app.data.repository

import com.flashshare.app.data.db.TransferRecordDao
import com.flashshare.app.data.model.TransferRecord
import kotlinx.coroutines.flow.Flow

class TransferHistoryRepository(private val dao: TransferRecordDao) {
    fun getAllTransfers(): Flow<List<TransferRecord>> = dao.getAllTransferRecords()

    fun getTransfersByType(type: String): Flow<List<TransferRecord>> =
        dao.getTransferRecordsByType(type)

    suspend fun saveTransfer(record: TransferRecord) = dao.insertTransferRecord(record)

    suspend fun clearAllHistory() = dao.deleteAllTransferRecords()

    suspend fun clearOldRecords(beforeTime: Long) = dao.deleteTransferRecordsOlderThan(beforeTime)
}
