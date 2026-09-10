package com.flashshare.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.flashshare.app.network.NearbyConnectionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoveryService : Service() {
    private lateinit var connectionManager: NearbyConnectionManager

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
