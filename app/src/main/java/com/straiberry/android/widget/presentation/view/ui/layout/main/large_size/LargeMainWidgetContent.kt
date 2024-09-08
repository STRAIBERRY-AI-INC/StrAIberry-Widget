package com.straiberry.android.widget.presentation.view.ui.layout.main.large_size

import android.content.Context
import android.widget.RemoteViews
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.straiberry.android.widget.R
import com.straiberry.android.widget.presentation.view.ui.actions.BrushingNowClickAction
import com.straiberry.android.widget.presentation.view.ui.actions.GoToActivityPageClickAction
import com.straiberry.android.widget.presentation.view.ui.theme.Blue700


val glanceBodyText21 = TextStyle(
    fontSize = 21.sp,
    fontWeight = FontWeight.Bold
)
val glanceBodyText18 = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold
)

@Composable
fun LargeMainWidgetContent(
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
    Box(modifier = GlanceModifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

        AndroidRemoteViews(remoteViews = BrushingCountView(context).apply {
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

    Row(modifier = GlanceModifier.padding(start = 30.dp, top = 33.dp)) {
        Image(
            provider = ImageProvider(R.drawable.ic_whitening),
            contentDescription = context.getString(
                R.string.whitening_icon_description
            ), modifier = GlanceModifier.size(34.dp, 27.dp)
        )

        Text(
            modifier = GlanceModifier.padding(start = 7.dp)
                .clickable(onClick = actionRunCallback<GoToActivityPageClickAction>()),
            text = context.getString(R.string.daily_goal),
            style = glanceBodyText21.copy(
                color = ColorProvider(Blue700)
            )
        )
    }
}

@Composable
fun BrushingButton(context: Context) {
    Box(modifier = GlanceModifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Image(
            provider = ImageProvider(R.drawable.brush_now),
            contentDescription = context.getString(
                R.string.brush_now_button
            ),
            modifier = GlanceModifier.clickable(onClick = actionRunCallback<BrushingNowClickAction>())
                .padding(top = 150.dp)
        )
        Image(
            provider = ImageProvider(R.drawable.pos_brush_now),
            contentDescription = context.getString(
                R.string.brush_now_button
            ),
        )
    }
}

class BrushingCountView(context: Context) :
    RemoteViews(context.packageName, R.layout.current_day_brushing_count_large_siz)
