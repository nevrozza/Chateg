package com.chateg.app.android

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class WManager(context : Context, params : WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.e("dsa", "sadasdasdasdasdasda")

        return Result.success()
    }
}

