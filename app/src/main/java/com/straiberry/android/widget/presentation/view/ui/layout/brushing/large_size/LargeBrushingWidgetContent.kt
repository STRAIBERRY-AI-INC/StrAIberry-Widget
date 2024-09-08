package com.straiberry.android.widget.presentation.view.ui.layout.brushing.large_size

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.straiberry.android.widget.R
import com.straiberry.android.widget.helper.Utility.formatTime
import com.straiberry.android.widget.presentation.view.ui.actions.BRUSHING_UPPER_JAW
import com.straiberry.android.widget.presentation.view.ui.actions.GoToMainPageClickAction
import com.straiberry.android.widget.presentation.view.ui.actions.StartBrushingClickAction
import com.straiberry.android.widget.presentation.view.ui.layout.main.large_size.glanceBodyText21
import com.straiberry.android.widget.presentation.view.ui.theme.Blue700


val glanceBodyText12 = TextStyle(
    fontSize = 12.sp,
)

@Composable
fun LargeBrushingWidgetContent(
    modifier: GlanceModifier,
    time: Long,
    timerStatus: Boolean,
    currentFrameOfAnimation: Int,
    brushingBothJawStatus: String,
) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        MainTitle(
            context = context,
            brushingBothJawStatus = brushingBothJawStatus
        )
        BrushingUpperJawAnimation(
            context = context,
            currentFrameOfAnimation = currentFrameOfAnimation,
            brushingBothJawStatus = brushingBothJawStatus
        )
        StartBrushingButton(
            context = context,
            countDownTimer = time.formatTime(),
            timerIsRunning = timerStatus
        )
    }
}

@Composable
fun MainTitle(context: Context, brushingBothJawStatus: String) {
    val titleText = if (brushingBothJawStatus == BRUSHING_UPPER_JAW)
        context.getString(R.string.brushing_your_upper_jaw)
    else
        context.getString(R.string.brushing_your_lower_jaw)

    Box(contentAlignment = Alignment.Center, modifier = GlanceModifier.fillMaxWidth()) {
        Text(
            modifier = GlanceModifier.padding(top = 36.dp),
            text = titleText,
            style = glanceBodyText12.copy(
                color = ColorProvider(Blue700)
            )
        )
    }


}

@Composable
fun BrushingUpperJawAnimation(
    context: Context,
    currentFrameOfAnimation: Int,
    brushingBothJawStatus: String
) {

    val listOfBrushingAnimation = if (brushingBothJawStatus == BRUSHING_UPPER_JAW)
        listOfUpperJawFrames
    else
        listOfLowerJawFrames

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = GlanceModifier.fillMaxWidth().padding(top = 50.dp)
    ) {
        Image(
            provider =
            ImageProvider(listOfBrushingAnimation[currentFrameOfAnimation]),
            contentDescription = context.getString(R.string.brushing_upper_jaw_animation)
        )

    }
}


@Composable
fun StartBrushingButton(context: Context, countDownTimer: String, timerIsRunning: Boolean) {
    Box(contentAlignment = Alignment.BottomCenter, modifier = GlanceModifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = GlanceModifier.padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = GlanceModifier.size(40.dp, 40.dp),
                    provider = ImageProvider(R.drawable.ic_time),
                    contentDescription = ""
                )
                Text(
                    modifier = GlanceModifier.padding(start = 40.dp),
                    text = countDownTimer,
                    style = glanceBodyText21.copy(
                        color = ColorProvider(Blue700)
                    )
                )

            }
            Image(
                provider = if (timerIsRunning)
                    ImageProvider(R.drawable.ic_start_large_size)
                else
                    ImageProvider(R.drawable.ic_pause_large_size),
                contentDescription = context.getString(
                    R.string.brush_now_button
                ),
                modifier = GlanceModifier.clickable(onClick = actionRunCallback<StartBrushingClickAction>())
                    .padding(bottom = 15.dp),
            )
        }

    }

}

@Composable
fun BrushingHasBeenFinished(modifier: GlanceModifier, context: Context) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = context.getString(R.string.have_fun_brushing_your_teeth),
            style = glanceBodyText21.copy(
                color = ColorProvider(Blue700),
                textAlign = TextAlign.Center
            )
        )

        Image(
            provider = ImageProvider(R.drawable.ic_brushing_is_done),
            modifier = GlanceModifier.size(160.dp, 160.dp).padding(top = 20.dp, bottom = 30.dp),
            contentDescription = context.getString(R.string.done)
        )

        Image(
            provider = ImageProvider(R.drawable.ic_done_button_large_size),
            contentDescription = context.getString(R.string.done),
            modifier = GlanceModifier.clickable(onClick = actionRunCallback<GoToMainPageClickAction>())
        )
    }
}


val listOfUpperJawFrames =
    listOf(
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_02,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_02,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_03,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_03,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_04,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_04,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_05,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_05,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_06,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_06,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_07,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_07,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_08,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_08,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_09,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_09,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_10,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_10,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_11,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_11,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_12,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_12,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_13,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_13,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_14,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_14,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_15,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_15,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_16,
        R.drawable.frame_upper_jaw_01,
        R.drawable.frame_upper_jaw_16,
        R.drawable.frame_upper_jaw_01,
    )


val listOfLowerJawFrames =
    listOf(
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_02,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_02,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_03,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_03,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_04,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_04,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_05,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_05,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_06,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_06,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_07,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_07,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_08,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_08,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_09,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_09,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_10,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_10,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_11,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_11,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_12,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_12,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_13,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_13,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_14,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_14,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_15,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_15,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_16,
        R.drawable.frame_lower_jaw_01,
        R.drawable.frame_lower_jaw_16,
        R.drawable.frame_lower_jaw_01,
    )