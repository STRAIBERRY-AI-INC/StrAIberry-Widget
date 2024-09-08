package com.straiberry.android.widget.presentation.workers

import android.content.Context
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.straiberry.android.widget.presentation.view.BrushingWidget
import com.straiberry.android.widget.presentation.view.ui.actions.TIMER_FLOW_CONTENT
import com.straiberry.android.widget.presentation.view.ui.actions.TIMER_IS_PAUSE
import com.straiberry.android.widget.presentation.view.ui.actions.TimerFlowKey
import com.straiberry.android.widget.presentation.view.ui.actions.TimerRunningStatusKey
import com.straiberry.android.widget.presentation.view.ui.actions.updateAnimationFrame
import com.straiberry.android.widget.presentation.view.ui.actions.updateCountDownTimer
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.runBlocking

@HiltWorker
class CountDownTimerWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workParams: WorkerParameters
) : CoroutineWorker(context, workParams) {
    companion object {
        var remainingTime: Long = TIMER_FLOW_CONTENT
        var isTimerRunning: String = TIMER_IS_PAUSE
   }

    override suspend fun doWork(): Result {
        getTimerData(context)

        Handler(Looper.getMainLooper()).post {
               object : CountDownTimer(remainingTime, 1000) {

                override fun onTick(millisRemaining: Long) {
                    runBlocking { getTimerData(context) }
                    if (isTimerRunning != TIMER_IS_PAUSE)
                        runBlocking {
                            updateCountDownTimer(millisRemaining, context)
                        }
                    else
                        cancel()
                }

                override fun onFinish() {
                    cancel()
                }
            }.start()

                object : CountDownTimer(remainingTime,1000){
                override fun onTick(millisUntilFinished: Long) {
                    runBlocking { getTimerData(context) }
                    if (isTimerRunning!= TIMER_IS_PAUSE)
                        runBlocking { updateAnimationFrame(context) }
                    else
                        cancel()
                }

                override fun onFinish() {
                    cancel()
                }

            }.start()
        }


        return Result.success()
    }

   suspend fun getTimerData(context: Context){
        GlanceAppWidgetManager(context).getGlanceIds(BrushingWidget::class.java)
            .forEach { glanceId ->
                updateAppWidgetState(context, glanceId) { prefs ->
                    remainingTime = prefs[TimerFlowKey] ?: TIMER_FLOW_CONTENT
                    isTimerRunning = prefs[TimerRunningStatusKey] ?: TIMER_IS_PAUSE
                }
            }
    }
}