package com.straiberry.android.widget.presentation.view.ui.layout.brushing

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.wrapContentSize
import com.straiberry.android.widget.R
import com.straiberry.android.widget.presentation.view.BrushingWidget.Companion.LARGE_SIZE
import com.straiberry.android.widget.presentation.view.BrushingWidget.Companion.MEDIUM_SIZE
import com.straiberry.android.widget.presentation.view.BrushingWidget.Companion.SMALL_SIZE
import com.straiberry.android.widget.presentation.view.ui.layout.brushing.large_size.BrushingHasBeenFinished
import com.straiberry.android.widget.presentation.view.ui.layout.brushing.large_size.LargeBrushingWidgetContent
import com.straiberry.android.widget.presentation.view.ui.layout.brushing.medium_size.BrushingHasBeenFinishedMediumSize
import com.straiberry.android.widget.presentation.view.ui.layout.brushing.medium_size.MediumBrushingWidgetContent
import com.straiberry.android.widget.presentation.view.ui.layout.brushing.small_size.BrushingHasBeenFinishedSmallSize
import com.straiberry.android.widget.presentation.view.ui.layout.brushing.small_size.SmallBrushingWidgetContent


@Composable
fun BrushingHasBeenDoneContent(
    widgetSize: DpSize
) {
    val context = LocalContext.current
    when {
        widgetSize.height >= LARGE_SIZE.height -> BrushingHasBeenFinished(
            modifier = GlanceModifier
                .wrapContentSize()
                .background(ImageProvider(R.drawable.brushing_background))
                .cornerRadius(24.dp),
            context = context
        )

        widgetSize.height >= MEDIUM_SIZE.height -> BrushingHasBeenFinishedMediumSize(
            modifier = GlanceModifier
                .wrapContentSize()
                .fillMaxWidth()
                .background(ImageProvider(R.drawable.brushing_background_for_medium_size))
                .cornerRadius(24.dp),
            context = context
        )

        widgetSize.width >= SMALL_SIZE.width -> BrushingHasBeenFinishedSmallSize(
            modifier = GlanceModifier
                .wrapContentSize()
                .background(ImageProvider(R.drawable.brushing_background_for_small_size))
                .cornerRadius(24.dp),
            context = context
        )
    }
}

@Composable
fun BrushingPageWidgetContent(
    time: Long,
    timerStatus: Boolean,
    currentFrameOfAnimation: Int,
    brushingBothJawStatus: String,
    widgetSize: DpSize,
) {

    when {
        widgetSize.height >= LARGE_SIZE.height -> LargeBrushingWidgetContent(
            modifier = GlanceModifier
                .wrapContentSize()
                .background(ImageProvider(R.drawable.brushing_background))
                .cornerRadius(24.dp),
            time = time,
            timerStatus = timerStatus,
            currentFrameOfAnimation = currentFrameOfAnimation,
            brushingBothJawStatus = brushingBothJawStatus,
        )

        widgetSize.height >= MEDIUM_SIZE.height -> MediumBrushingWidgetContent(
            modifier = GlanceModifier
                .wrapContentSize()
                .background(ImageProvider(R.drawable.brushing_background_for_medium_size))
                .cornerRadius(24.dp),
            time = time,
            timerStatus = timerStatus,
            currentFrameOfAnimation = currentFrameOfAnimation,
            brushingBothJawStatus = brushingBothJawStatus,
        )

        widgetSize.width >= SMALL_SIZE.width -> SmallBrushingWidgetContent(
            modifier = GlanceModifier
                .wrapContentSize()
                .background(ImageProvider(R.drawable.brushing_background_for_small_size))
                .cornerRadius(24.dp),
            time = time,
            timerStatus = timerStatus,
            currentFrameOfAnimation = currentFrameOfAnimation,
            brushingBothJawStatus = brushingBothJawStatus,
        )
    }
}