package com.chateg.app.android

import MainRepository
import PlatformConfiguration
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.arkivanov.essenty.lifecycle.Lifecycle
import createNotification
import di.Inject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class WManager(context : Context, params : WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val mainRepository: MainRepository = Inject.instance()
        GlobalScope.launch {
            val lifecycle = mainRepository.fetchLifecycle()
            if(lifecycle == Lifecycle.State.DESTROYED.name) {
                val gotChatsMap = mainRepository.fetchGotMessages()
                val onlineChats = mainRepository.fetchOnlineMessages()
                val onlineChatsMap = mutableMapOf<String, String>()
                for (i in onlineChats) {
                    onlineChatsMap[i.nick] = i.onlineMessagesCount
                    if ((gotChatsMap[i.nick] ?: i.onlineMessagesCount) !=  i.onlineMessagesCount) {


                        createNotification("Новое сообщение от ${i.nick}")

                    }
                }
                mainRepository.saveGotMessages(onlineChatsMap.toString())
            }
        }

        return Result.success()
    }
}

