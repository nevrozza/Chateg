package com.chateg.app.android

import MainRepository
import android.content.Context
import android.util.Log
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
            if(lifecycle != Lifecycle.State.DESTROYED.name) {
                val gotChatsMap = mainRepository.fetchGotMessages()
                val onlineChats = mainRepository.fetchOnlineMessages()
                val onlineChatsMap = mutableMapOf<String, Int>()
                for (i in onlineChats) {
                    onlineChatsMap[i.nick] = i.onlineMessagesCount
                    if ((gotChatsMap[i.nick] ?: i.onlineMessagesCount) < i.onlineMessagesCount) {


                        createNotification("Новое сообщение от ${i.nick}")

                    }
                }
                mainRepository.saveGotMessages(onlineChatsMap.toString())
            }
        }

        return Result.success()
    }
}

