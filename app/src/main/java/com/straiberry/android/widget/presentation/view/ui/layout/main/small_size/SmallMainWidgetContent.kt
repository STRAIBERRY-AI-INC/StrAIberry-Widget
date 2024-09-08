package com.straiberry.android.widget.presentation.view.ui.layout.main.small_size

import android.content.Context
import android.widget.RemoteViews
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.AndroidRemoteViews
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import com.straiberry.android.widget.R
import com.straiberry.android.widget.presentation.view.ui.actions.BrushingNowClickAction
import com.straiberry.android.widget.presentation.view.ui.actions.GoToActivityPageClickAction
import com.straiberry.android.widget.presentation.view.ui.layout.main.medium_size.glanceBodyText14
import com.straiberry.android.widget.presentation.view.ui.theme.Blue700


@Composable
fun SmallMainWidgetContent(
    modifier: GlanceModifier,
    brushCount: Int,
    remindBrush: String,
    lastBrushTime: String
) {
    Column(modifier = modifier) {
        val context = LocalContext.current
        MainTitle(context = context)
        LastBrushStatus(
            context = context,
            brushCount = brushCount,
            lastBrushTime = lastBrushTime,
            remindBrush = remindBrush
        )
        BrushingButton(context = context)
    }
}

@Composable
fun LastBrushStatus(context: Context, brushCount: Int, lastBrushTime: String, remindBrush: String) {
    Box(modifier = GlanceModifier.padding(start = 23.dp)) {

        AndroidRemoteViews(remoteViews = BrushingCountViewSmallSize(context).apply {
            setTextViewText(
                R.id.textViewBrushingCount,
                context.getString(R.string.brushing_count, brushCount.toString(), remindBrush)
            )
            setTextViewText(R.id.textViewLastBrushingTime, lastBrushTime)

            setProgressBar(
                R.id.progress_in_brushing,
                remindBrush.toInt(),
                brushCount,
                false
            )
        })
        Image(
            provider = ImageProvider(R.drawable.ic_empty), contentDescription = "",
            modifier = GlanceModifier.size(width = 120.dp, height = 70.dp)
                .clickable(onClick = actionRunCallback<GoToActivityPageClickAction>())
        )

    }

}

@Composable
fun MainTitle(context: Context) {

    Row(
        modifier = GlanceModifier.padding(start = 18.dp, top = 19.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            provider = ImageProvider(R.drawable.ic_whitening),
            contentDescription = context.getString(
                R.string.whitening_icon_description
            ), modifier = GlanceModifier.size(23.dp, 18.dp)
        )

        Text(
            modifier = GlanceModifier.padding(start = 7.dp)
                .clickable(onClick = actionRunCallback<GoToActivityPageClickAction>()),
            text = context.getString(R.string.daily_goal),
            style = glanceBodyText14.copy(
                color = ColorProvider(Blue700)
            )
        )
    }
}

@Composable
fun BrushingButton(context: Context) {
    Box(modifier = GlanceModifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Image(
            provider = ImageProvider(R.drawable.ic_brush_now_medium_size),
            contentDescription = context.getString(
                R.string.brush_now_button
            ),
            modifier = GlanceModifier.clickable(onClick = actionRunCallback<BrushingNowClickAction>())
                .padding(top = 28.dp)
        )
    }
}

class BrushingCountViewSmallSize(context: Context) :
    RemoteViews(context.packageName, R.layout.current_day_brushing_count_small_siz)
