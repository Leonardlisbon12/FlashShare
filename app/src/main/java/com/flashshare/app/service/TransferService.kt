package com.flashshare.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.app.PendingIntent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.flashshare.app.R
import com.flashshare.app.ui.MainActivity
import com.flashshare.app.data.model.TransferProgress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class TransferService : Service(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    
    companion object {
        const val NOTIFICATION_ID = 1001
        const val CHANNEL_ID = "flashshare_transfer"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("FlashShare Transfer")
            .setContentText("Preparing transfer...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(getPendingIntent())
            .setProgress(100, 0, true)
            .build()

        startForeground(NOTIFICATION_ID, notification)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "FlashShare Transfers",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        }
    }

    private fun getPendingIntent(): PendingIntent {
        return PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun updateTransferProgress(progress: TransferProgress) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Transferring: ${progress.currentFileName}")
            .setContentText("${progress.currentFileIndex}/${progress.totalFiles} files - ${progress.percentage}%")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setProgress(100, progress.percentage, false)
            .setContentIntent(getPendingIntent())
            .build()

        getSystemService(NotificationManager::class.java)?.notify(NOTIFICATION_ID, notification)
    }
}
