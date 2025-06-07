package ru.practice.t_finance.app

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationHandler: NotificationHandler


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val titleText = message.data[DATA_TITLE] ?: "title"
        val messageText = message.data[DATA_MESSAGE] ?: "message"
        notificationHandler.showNotification(titleText, messageText)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("MyLog","TOKEN - $token")
    }


    companion object{
        const val DATA_TITLE = "title"
        const val DATA_MESSAGE = "message"
    }
}