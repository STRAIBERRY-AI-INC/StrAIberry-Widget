package com.straiberry.android.widget.presentation.view.ui.layout.activity

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.wrapContentSize
import com.straiberry.android.widget.R
import com.straiberry.android.widget.domain.model.BrushingModel
import com.straiberry.android.widget.presentation.view.BrushingWidget.Companion.LARGE_SIZE
import com.straiberry.android.widget.presentation.view.BrushingWidget.Companion.MEDIUM_SIZE
import com.straiberry.android.widget.presentation.view.BrushingWidget.Companion.SMALL_SIZE
import com.straiberry.android.widget.presentation.view.ui.layout.activity.large_size.LargeActivityWidgetContent
import com.straiberry.android.widget.presentation.view.ui.layout.activity.medium_size.MediumActivityWidgetContent
import com.straiberry.android.widget.presentation.view.ui.layout.activity.small_size.SmallActivityWidgetContent


@Composable
fun ActivityPageWidgetContent(
    lastBrushTime: String,
    listOfBrushingModels: List<BrushingModel>,
    remindBrush: String,
    widgetSize: DpSize
) {
    val modifier = GlanceModifier
        .wrapContentSize()
        .background(ImageProvider(R.drawable.background))
        .cornerRadius(24.dp)
    when {
        widgetSize.height >= LARGE_SIZE.height -> LargeActivityWidgetContent(
            modifier = modifier,
            lastBrushTime = lastBrushTime,
            listOfBrushingModels = listOfBrushingModels,
            remindBrush = remindBrush
        )

        widgetSize.height >= MEDIUM_SIZE.height -> MediumActivityWidgetContent(
            modifier = modifier,
            lastBrushTime = lastBrushTime,
            listOfBrushingModels = listOfBrushingModels,
            remindBrush = remindBrush
        )
        widgetSize.width >= SMALL_SIZE.width -> SmallActivityWidgetContent(
            modifier = modifier,
            remindBrush = remindBrush,
            listOfBrushingModels = listOfBrushingModels
        )
    }
}