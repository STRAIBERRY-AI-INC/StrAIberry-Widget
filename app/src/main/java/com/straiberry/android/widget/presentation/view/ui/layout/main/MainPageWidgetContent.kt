package com.straiberry.android.widget.presentation.view.ui.layout.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.wrapContentSize
import com.straiberry.android.widget.R
import com.straiberry.android.widget.presentation.view.BrushingWidget.Companion.LARGE_SIZE
import com.straiberry.android.widget.presentation.view.BrushingWidget.Companion.MEDIUM_SIZE
import com.straiberry.android.widget.presentation.view.BrushingWidget.Companion.SMALL_SIZE
import com.straiberry.android.widget.presentation.view.ui.layout.main.large_size.LargeMainWidgetContent
import com.straiberry.android.widget.presentation.view.ui.layout.main.medium_size.MediumMainWidgetContent
import com.straiberry.android.widget.presentation.view.ui.layout.main.small_size.SmallMainWidgetContent

@Composable
fun MainPageWidgetContent(
    todayBrushingCount: Int,
    lastBrush: String,
    remindBrush:String,
    widgetSize: DpSize
) {
    val glanceModifier = GlanceModifier
        .wrapContentSize()
        .background(ImageProvider(R.drawable.background))
        .cornerRadius(24.dp)

    when {
        widgetSize.height >= LARGE_SIZE.height -> LargeMainWidgetContent(
            modifier = glanceModifier,
            brushCount = todayBrushingCount,
            remindBrush = remindBrush,
            lastBrushTime = lastBrush
        )

        widgetSize.height >= MEDIUM_SIZE.height -> MediumMainWidgetContent(
            modifier = glanceModifier,
            brushCount = todayBrushingCount,
            remindBrush = remindBrush,
            lastBrushTime = lastBrush
        )

        widgetSize.width >= SMALL_SIZE.width -> SmallMainWidgetContent(
            modifier = glanceModifier,
            brushCount = todayBrushingCount,
            remindBrush = remindBrush,
            lastBrushTime = lastBrush
        )
    }
}