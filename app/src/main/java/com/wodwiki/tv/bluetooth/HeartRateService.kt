package com.wodwiki.tv.bluetooth

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import timber.log.Timber

class HeartRateService : Service() {
    
    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "heart_rate_service"
    }
    
    override fun onCreate() {
        super.onCreate()
        Timber.d("HeartRateService created")
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("HeartRateService started")
        
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Heart Rate Monitor")
            .setContentText("Monitoring heart rate...")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
        
        startForeground(NOTIFICATION_ID, notification)
        
        return START_STICKY
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Timber.d("HeartRateService destroyed")
    }
    
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}