package com.straiberry.android.widget.presentation.view.ui.layout.brushing.medium_size

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
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.straiberry.android.widget.R
import com.straiberry.android.widget.helper.Utility.formatTime
import com.straiberry.android.widget.presentation.view.ui.actions.BRUSHING_UPPER_JAW
import com.straiberry.android.widget.presentation.view.ui.actions.GoToMainPageClickAction
import com.straiberry.android.widget.presentation.view.ui.actions.StartBrushingClickAction
import com.straiberry.android.widget.presentation.view.ui.layout.brushing.large_size.listOfLowerJawFrames
import com.straiberry.android.widget.presentation.view.ui.layout.brushing.large_size.listOfUpperJawFrames
import com.straiberry.android.widget.presentation.view.ui.layout.main.large_size.glanceBodyText18
import com.straiberry.android.widget.presentation.view.ui.theme.Blue700
import com.straiberry.android.widget.presentation.view.ui.theme.Green500

val glanceBodyText12 = TextStyle(
    fontSize = 12.sp,
)

@Composable
fun MediumBrushingWidgetContent(
    modifier: GlanceModifier,
    time: Long,
    timerStatus: Boolean,
    currentFrameOfAnimation: Int,
    brushingBothJawStatus: String,
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        Row(modifier = GlanceModifier.fillMaxWidth()) {
            MainTitle(
                context = context,
                brushingBothJawStatus = brushingBothJawStatus
            )
            BrushingJawAnimation(
                context = context,
                currentFrameOfAnimation = currentFrameOfAnimation,
                brushingBothJawStatus = brushingBothJawStatus
            )
        }

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

    Box {
        Text(
            modifier = GlanceModifier.padding(top = 40.dp, start = 34.dp),
            text = titleText,
            style = glanceBodyText12.copy(
                color = ColorProvider(Green500)
            )
        )
    }


}

@Composable
fun BrushingJawAnimation(
    context: Context,
    currentFrameOfAnimation: Int,
    brushingBothJawStatus: String
) {

    val listOfBrushingAnimation = if (brushingBothJawStatus == BRUSHING_UPPER_JAW)
        listOfUpperJawFrames
    else
        listOfLowerJawFrames

    Box(
        modifier = GlanceModifier.size(186.dp, 145.dp).padding(top = 26.dp, start = 20.dp)
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
    Row(
        modifier = GlanceModifier.fillMaxWidth().padding(bottom = 10.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Image(
            provider = if (timerIsRunning)
                ImageProvider(R.drawable.ic_start_medium_size)
            else
                ImageProvider(R.drawable.ic_pause_medium_size),
            contentDescription = context.getString(
                R.string.brush_now_button
            ),
            modifier = GlanceModifier.clickable(onClick = actionRunCallback<StartBrushingClickAction>())
                .padding(start = 40.dp),
        )

        Row(
            modifier = GlanceModifier.padding(start = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = GlanceModifier.size(30.dp, 30.dp),
                provider = ImageProvider(R.drawable.ic_time),
                contentDescription = ""
            )
            Text(
                modifier = GlanceModifier.padding(start = 10.dp),
                text = countDownTimer,
                style = glanceBodyText18.copy(
                    color = ColorProvider(Blue700)
                )
            )

        }
    }

}

@Composable
fun BrushingHasBeenFinishedMediumSize(modifier: GlanceModifier, context: Context) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = context.getString(R.string.have_fun_brushing_your_teeth_),
            style = glanceBodyText12.copy(
                color = ColorProvider(Blue700)
            )
        )

        Image(
            modifier = GlanceModifier.size(83.dp, 83.dp).padding(top = 20.dp, bottom = 20.dp),
            provider = ImageProvider(R.drawable.ic_brushing_is_done),
            contentDescription = context.getString(R.string.done)
        )

        Image(
            provider = ImageProvider(R.drawable.ic_done_button_medium_size),
            contentDescription = context.getString(R.string.done),
            modifier = GlanceModifier.clickable(onClick = actionRunCallback<GoToMainPageClickAction>())
        )
    }
}