package com.straiberry.android.widget.presentation.view.ui.actions

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.*
import com.straiberry.android.widget.presentation.view.BrushingWidget
import com.straiberry.android.widget.presentation.workers.AddNewBrushingWorker
import com.straiberry.android.widget.presentation.workers.CountDownTimerWorker
import com.straiberry.android.widget.presentation.workers.DelayedWidgetWorker
import com.straiberry.android.widget.presentation.workers.GetAllBrushingWorker
import java.util.concurrent.TimeUnit

val BrushingPerDayKey = intPreferencesKey("brushing_per_day")
val PageTypeKey = stringPreferencesKey("page_type")
val TimerFlowKey = longPreferencesKey("timer_flow")
val TimerRunningStatusKey = stringPreferencesKey("is_timer_running")
val CurrentFrameOfAnimation = intPreferencesKey("current_frame_of_animation")
val BrushingBothJawStatus = stringPreferencesKey("brushing_is_finished")
val ListOfAllBrushingKey = stringPreferencesKey("list_of_all_brushing")
val ListOfAllBrushingIsUpdatedKey = booleanPreferencesKey("list_of_all_brushing_is_updated")

const val BRUSHING_UPPER_JAW = "brushing_upper_jaw"
const val BRUSHING_LOWER_JAW = "brushing_lower_jaw"
const val BRUSHING_IS_FINISHED = "brushing_is_finished"
const val TIMER_IS_RUNNING = "1"
const val TIMER_IS_PAUSE = "0"
const val PAGE_TYPE_MAIN = "main_page"
const val TIMER_FLOW_CONTENT = 60000L
const val TIMER_FLOW_FOR_ONE_SECOND = 1000L
const val ADD_NEW_BRUSHING_WORK_NAME = "AddNewBrushingWorker"
const val GET_ALL_BRUSHING_WORK_NAME = "GetAllBrushingWorker"
const val COUNT_DOWN_TIMER_WORK_NAME = "CountDownTimerWorker"
const val TOTAL_ANIMATION_FRAME = 59
const val PAGE_TYPE_BRUSHING = "brushing_page"
const val PAGE_TYPE_STATUS = "status_page"
const val PAGE_TYPE_BRUSHING_HAS_BEEN_COMPLETE = "brushing_is_completed_page"


class BrushingNowClickAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            prefs[PageTypeKey] = PAGE_TYPE_BRUSHING
            prefs[BrushingBothJawStatus] = BRUSHING_UPPER_JAW
        }

        BrushingWidget().update(context, glanceId)
    }

}

class GoToActivityPageClickAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) {
            it[PageTypeKey] = PAGE_TYPE_STATUS
        }
        BrushingWidget().update(context, glanceId)
    }

}


class GoToMainPageClickAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            prefs[PageTypeKey] = PAGE_TYPE_MAIN
        }
        BrushingWidget().update(context, glanceId)
    }

}

/**
 * This will update the ticker value in prefs.
 * When a Two minute count down is finished ,we check that this two minute was for one jaw or
 * it has been finished brushing. If one jaw has been finished then we reset data for brushing another jaw otherwise
 * brushing has been finished and we a add new record to local store.
 */
suspend fun updateCountDownTimer(timeValue: Long, context: Context) {
    GlanceAppWidgetManager(context).getGlanceIds(BrushingWidget::class.java).forEach { glanceId ->
        updateAppWidgetState(context, glanceId) { prefs ->
            prefs[TimerFlowKey] = timeValue
            if (timeValue <= TIMER_FLOW_FOR_ONE_SECOND) {
                if (prefs[BrushingBothJawStatus] == BRUSHING_UPPER_JAW) {
                    prefs[TimerFlowKey] = TIMER_FLOW_CONTENT
                    prefs[BrushingBothJawStatus] = BRUSHING_LOWER_JAW
                    runCountDownTimerWorker(context)
                } else if (prefs[BrushingBothJawStatus] == BRUSHING_LOWER_JAW) {
                    prefs[BrushingBothJawStatus] = BRUSHING_IS_FINISHED
                    prefs[PageTypeKey] = PAGE_TYPE_BRUSHING_HAS_BEEN_COMPLETE
                    prefs[ListOfAllBrushingIsUpdatedKey] = false
                    prefs[TimerFlowKey] = TIMER_FLOW_CONTENT
                    prefs[TimerRunningStatusKey] = TIMER_IS_PAUSE
                    prefs[CurrentFrameOfAnimation] = 0
                    addNewBrushingDate(context)
                }
            }

        }
    }
    BrushingWidget().updateAll(context)
}


/**
 * Updating animation frame based on timer ticker. A separate timer has been set for updating brushing animation
 * frames. Total animation frame is 60 and the frame will replaced with next based on timer ticker.
 */
suspend fun updateAnimationFrame(context: Context) {
    GlanceAppWidgetManager(context).getGlanceIds(BrushingWidget::class.java).forEach { glanceId ->
        updateAppWidgetState(context, glanceId) { prefs ->
            if (prefs[CurrentFrameOfAnimation] == null || prefs[CurrentFrameOfAnimation]!! >= TOTAL_ANIMATION_FRAME)
                prefs[CurrentFrameOfAnimation] = 0
            else
                prefs[CurrentFrameOfAnimation] = prefs[CurrentFrameOfAnimation]!! + 1
        }
    }
    BrushingWidget().updateAll(context)
}


suspend fun getListOfAllBrushing(context: Context, listOfBrushing: String) {
    GlanceAppWidgetManager(context).getGlanceIds(BrushingWidget::class.java).forEach { glanceId ->
        updateAppWidgetState(context, glanceId) { prefs ->
            prefs[ListOfAllBrushingKey] = listOfBrushing
            prefs[ListOfAllBrushingIsUpdatedKey] = true
        }
    }
    BrushingWidget().updateAll(context)
}

fun dummyDelayedWorkerToDecreaseFlickering(context: Context) {
    WorkManager.getInstance(context).enqueueUniqueWork(
        DelayedWidgetWorker.TAG,
        ExistingWorkPolicy.KEEP,
        OneTimeWorkRequestBuilder<DelayedWidgetWorker>()
            .setInitialDelay(10 * 365, TimeUnit.DAYS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresCharging(true)
                    .build()
            )
            .build()
    )

}

fun addNewBrushingDate(context: Context) {
    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            ADD_NEW_BRUSHING_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.from(AddNewBrushingWorker::class.java)
        )

}

fun getAllBrushing(context: Context) {
    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            GET_ALL_BRUSHING_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.from(GetAllBrushingWorker::class.java)
        )
}

fun runCountDownTimerWorker(context: Context) {
    dummyDelayedWorkerToDecreaseFlickering(context)

    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            COUNT_DOWN_TIMER_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            OneTimeWorkRequest.from(CountDownTimerWorker::class.java)
        )
}

class StartBrushingClickAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        runCountDownTimerWorker(context)

        updateAppWidgetState(context, glanceId) { prefs ->
            if (prefs[TimerRunningStatusKey] == TIMER_IS_RUNNING) {
                prefs[TimerRunningStatusKey] = TIMER_IS_PAUSE
            } else {
                prefs[TimerRunningStatusKey] = TIMER_IS_RUNNING
            }
        }
        BrushingWidget().update(context, glanceId)
    }

}