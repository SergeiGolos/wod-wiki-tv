package com.example.gymtvapp.core

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.gymtvapp.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GymSessionService : Service() {

    // Example: Inject a dependency if needed for the service
    // @Inject lateinit var webSocketServer: WebSocketServer

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "GymSessionServiceChannel"
        const val NOTIFICATION_ID = 1
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Gym Session Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    private fun createNotification(): Notification {
        // Customize this notification for a TV environment
        // It might be less visual or have different interactions on TV
        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.foreground_service_notification_title))
            .setContentText(getString(R.string.foreground_service_notification_message))
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with a suitable icon
            // Add actions or more details if necessary
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start WebSocket server or other long-running tasks here
        // webSocketServer.start()

        // If the service is killed, it will be restarted
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop WebSocket server or clean up resources
        // webSocketServer.stop()
    }
}
