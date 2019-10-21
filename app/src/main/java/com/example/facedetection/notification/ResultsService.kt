package com.example.facedetection.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.facedetection.MainActivity
import com.example.facedetection.R
import com.example.facedetection.managers.FaceDetectorManager

class ResultsService : Service() {


    private val CHANNEL_ID = "channel1"
    private val NOTIFICATION_TITLE = "FaceDetection"
    private lateinit var notificationManager: NotificationManager
    private lateinit var observer: Observer<Boolean>
    private lateinit var liveData: MutableLiveData<Boolean>



    override fun onBind(intent: Intent?): IBinder? {
        return null
    }



    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        initObservable()

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification("processing images..."))

        return START_NOT_STICKY
    }


    private fun initObservable() {
        liveData = FaceDetectorManager.INSTANCE.isStartedDetectionLiveData

        observer = Observer { needToShowResults ->
            if (!needToShowResults) {
                showResults()
            }
        }

        liveData.observeForever(observer)

    }

    private fun showResults() {
        updateNotification()
    }

    private fun updateNotification() {
        startForeground(1, createNotification(getResultsText()))
    }

    private fun getResultsText(): String {
        var sizeOfFacesFound = 0
        var totalSize = 0

        FaceDetectorManager.INSTANCE.imagesWithFacesLiveData.value?.let {
            sizeOfFacesFound = it.size
        }
        FaceDetectorManager.INSTANCE.imagesWithoutFacesLiveData.value?.let {
            totalSize = it.size + sizeOfFacesFound
        }

        return "Found $sizeOfFacesFound from $totalSize"
    }

    private fun createNotification(text: String): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        createNotificationChannel()

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_faces)
            .setContentIntent(pendingIntent)
            .setChannelId(CHANNEL_ID)
            .build()

    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val notificationChannel =
            NotificationChannel(CHANNEL_ID, "Channel 1", NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    override fun onDestroy() {
        super.onDestroy()
        liveData.removeObserver(observer)
        stopSelf()
    }


}