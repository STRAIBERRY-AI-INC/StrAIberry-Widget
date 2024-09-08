package com.straiberry.android.widget.presentation.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class DelayedWidgetWorker(
    appContext: Context,
    workerParams: WorkerParameters,
): CoroutineWorker(appContext, workerParams) {

    companion object{
        const val TAG = "appWidgetWorkerKeepEnabled"
    }

    override suspend fun doWork(): Result {
        return Result.success()
    }
}