package com.flashshare.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.flashshare.app.data.model.TransferRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface TransferRecordDao {
    @Insert
    suspend fun insertTransferRecord(record: TransferRecord)

    @Query("SELECT * FROM transfer_records ORDER BY timestamp DESC")
    fun getAllTransferRecords(): Flow<List<TransferRecord>>

    @Query("SELECT * FROM transfer_records WHERE transferType = :type ORDER BY timestamp DESC")
    fun getTransferRecordsByType(type: String): Flow<List<TransferRecord>>

    @Query("DELETE FROM transfer_records")
    suspend fun deleteAllTransferRecords()

    @Query("DELETE FROM transfer_records WHERE timestamp < :beforeTime")
    suspend fun deleteTransferRecordsOlderThan(beforeTime: Long)

    @Query("SELECT COUNT(*) FROM transfer_records")
    suspend fun getTransferRecordCount(): Int
}
