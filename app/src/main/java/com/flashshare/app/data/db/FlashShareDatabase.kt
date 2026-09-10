package com.flashshare.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flashshare.app.data.model.TransferRecord

@Database(entities = [TransferRecord::class], version = 1, exportSchema = false)
abstract class FlashShareDatabase : RoomDatabase() {
    abstract fun transferRecordDao(): TransferRecordDao
}
