package com.straiberry.android.widget.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.currentState
import com.straiberry.android.widget.R
import com.straiberry.android.widget.helper.Utility.convertToBrushingModel
import com.straiberry.android.widget.helper.Utility.getCurrentDayBrushingCount
import com.straiberry.android.widget.helper.Utility.getLastBrush
import com.straiberry.android.widget.presentation.view.ui.actions.BRUSHING_UPPER_JAW
import com.straiberry.android.widget.presentation.view.ui.actions.BrushingBothJawStatus
import com.straiberry.android.widget.presentation.view.ui.actions.BrushingPerDayKey
import com.straiberry.android.widget.presentation.view.ui.actions.CurrentFrameOfAnimation
import com.straiberry.android.widget.presentation.view.ui.actions.ListOfAllBrushingIsUpdatedKey
import com.straiberry.android.widget.presentation.view.ui.actions.ListOfAllBrushingKey
import com.straiberry.android.widget.presentation.view.ui.actions.PAGE_TYPE_BRUSHING
import com.straiberry.android.widget.presentation.view.ui.actions.PAGE_TYPE_BRUSHING_HAS_BEEN_COMPLETE
import com.straiberry.android.widget.presentation.view.ui.actions.PAGE_TYPE_MAIN
import com.straiberry.android.widget.presentation.view.ui.actions.PAGE_TYPE_STATUS
import com.straiberry.android.widget.presentation.view.ui.actions.PageTypeKey
import com.straiberry.android.widget.presentation.view.ui.actions.TIMER_FLOW_CONTENT
import com.straiberry.android.widget.presentation.view.ui.actions.TIMER_IS_PAUSE
import com.straiberry.android.widget.presentation.view.ui.actions.TimerFlowKey
import com.straiberry.android.widget.presentation.view.ui.actions.TimerRunningStatusKey
import com.straiberry.android.widget.presentation.view.ui.actions.getAllBrushing
import com.straiberry.android.widget.presentation.view.ui.layout.activity.ActivityPageWidgetContent
import com.straiberry.android.widget.presentation.view.ui.layout.brushing.BrushingHasBeenDoneContent
import com.straiberry.android.widget.presentation.view.ui.layout.brushing.BrushingPageWidgetContent
import com.straiberry.android.widget.presentation.view.ui.layout.main.MainPageWidgetContent


class BrushingWidget : GlanceAppWidget() {
    companion object {
        val LARGE_SIZE = DpSize(349.dp, 337.dp)
        val MEDIUM_SIZE = DpSize(349.dp, 170.dp)
        val SMALL_SIZE = DpSize(170.dp, 80.dp)
    }

    override val sizeMode = SizeMode.Responsive(setOf(LARGE_SIZE, MEDIUM_SIZE, SMALL_SIZE))

    @Composable
    override fun Content() {

        val currentPage = currentState(key = PageTypeKey) ?: PAGE_TYPE_MAIN
        val timerState = currentState(key = TimerFlowKey) ?: TIMER_FLOW_CONTENT
        val isTimerRunning = currentState(key = TimerRunningStatusKey) ?: TIMER_IS_PAUSE
        val currentFrameOfAnimation = currentState(key = CurrentFrameOfAnimation) ?: 0
        val brushingBothJawStatus = currentState(key = BrushingBothJawStatus) ?: BRUSHING_UPPER_JAW
        val listOfAllBrushing = currentState(key = ListOfAllBrushingKey) ?: ""
        val brushingPerDay = currentState(key = BrushingPerDayKey) ?: 3
        val isListOfBrushingUpdated = currentState(key = ListOfAllBrushingIsUpdatedKey) ?: false
        val widgetSize = LocalSize.current

        var todayBrushingCount = 0
        var lastBrush = LocalContext.current.getString(R.string.no_brushing_yet)

        if (isListOfBrushingUpdated.not())
            getAllBrushing(LocalContext.current)

        if (listOfAllBrushing != "" && listOfAllBrushing.convertToBrushingModel().isNotEmpty()) {
            todayBrushingCount =
                listOfAllBrushing.convertToBrushingModel().getCurrentDayBrushingCount()
            lastBrush = listOfAllBrushing.convertToBrushingModel().getLastBrush()
        }

        when (currentPage) {
            PAGE_TYPE_MAIN -> MainPageWidgetContent(
                todayBrushingCount = todayBrushingCount,
                lastBrush = lastBrush,
                widgetSize = widgetSize,
                remindBrush = brushingPerDay.toString()
            )

            PAGE_TYPE_BRUSHING -> BrushingPageWidgetContent(
                time = timerState,
                timerStatus = isTimerRunning == TIMER_IS_PAUSE,
                currentFrameOfAnimation = currentFrameOfAnimation,
                brushingBothJawStatus = brushingBothJawStatus,
                widgetSize = widgetSize,
            )

            PAGE_TYPE_STATUS -> ActivityPageWidgetContent(
                lastBrushTime = lastBrush,
                listOfBrushingModels = if (listOfAllBrushing != "")
                    listOfAllBrushing.convertToBrushingModel()
                else
                    listOf(),
                remindBrush = brushingPerDay.toString(),
                widgetSize = widgetSize
            )

            PAGE_TYPE_BRUSHING_HAS_BEEN_COMPLETE -> BrushingHasBeenDoneContent(
                widgetSize = widgetSize
            )
        }
    }
}

class BrushingWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = BrushingWidget()
}



