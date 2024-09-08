package com.straiberry.android.widget.presentation.view.ui.layout.activity.medium_size

import android.content.Context
import android.view.View
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
import androidx.glance.appwidget.lazy.GridCells
import androidx.glance.appwidget.lazy.LazyVerticalGrid
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import com.straiberry.android.widget.R
import com.straiberry.android.widget.domain.model.BrushingModel
import com.straiberry.android.widget.helper.Utility.getDayFromDate
import com.straiberry.android.widget.helper.Utility.getDayNameFromDate
import com.straiberry.android.widget.helper.Utility.getMonthFromDate
import com.straiberry.android.widget.presentation.view.ui.actions.BrushingNowClickAction
import com.straiberry.android.widget.presentation.view.ui.layout.brushing.small_size.glanceBodyText12
import com.straiberry.android.widget.presentation.view.ui.theme.Blue700
import com.straiberry.android.widget.presentation.view.ui.theme.White
import java.util.Date


@Composable
fun MediumActivityWidgetContent(
    modifier: GlanceModifier,
    lastBrushTime: String,
    listOfBrushingModels: List<BrushingModel>,
    remindBrush: String
) {
Column(modifier = modifier) {
        val context = LocalContext.current
        MainTitle(context = context, lastBrushTime = lastBrushTime)
        ListOfBrushingCountForEachDay(
            context = context,
            listOfBrushingModels = listOfBrushingModels,
            remindBrush = remindBrush
        )
        BrushNowButton(context = context)
    }
}

@Composable
fun MainTitle(context: Context, lastBrushTime: String) {

    Row(modifier = GlanceModifier.padding(start = 22.dp, top = 19.dp)) {
        Image(
            provider = ImageProvider(R.drawable.ic_whitening),
            contentDescription = context.getString(
                R.string.whitening_icon_description
            ), modifier = GlanceModifier.size(23.dp, 18.dp)
        )
        Text(
            modifier = GlanceModifier.padding(start = 7.dp),
            text = context.getString(R.string.daily_goal),
            style = glanceBodyText12.copy(
                color = ColorProvider(Blue700),
                fontWeight = FontWeight.Bold
            )
        )

        Row(modifier = GlanceModifier.padding(start = 90.dp)) {
            Text(
                modifier = GlanceModifier.padding(start = 7.dp),
                text = context.getString(R.string.last_brushing),
                style = glanceBodyText12.copy(
                    color = ColorProvider(White),
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                modifier = GlanceModifier.padding(start = 7.dp),
                text = lastBrushTime,
                style = glanceBodyText12.copy(
                    color = ColorProvider(Blue700),
                    fontWeight = FontWeight.Bold
                )
            )
        }

    }
}

@Composable
fun ListOfBrushingCountForEachDay(
    context: Context,
    listOfBrushingModels: List<BrushingModel>,
    remindBrush: String
) {

    val listOfLastWeekBrushingData = if (listOfBrushingModels.size>=7)
        listOfBrushingModels.subList(0,6)
    else
        listOfBrushingModels


    LazyVerticalGrid(
        content = {
            items(listOfLastWeekBrushingData.size) {
                AndroidRemoteViews(remoteViews = ItemBrushingCountMediumView(context = context).apply {
                    val getDayName = listOfLastWeekBrushingData[it].brushingDate.getDayNameFromDate()
                    val brushingCount = listOfLastWeekBrushingData[it].brushingCount

                    if (listOfLastWeekBrushingData[it].brushingDate.getDayFromDate() == Date().getDayFromDate()
                        && listOfLastWeekBrushingData[it].brushingDate.getMonthFromDate() == Date().getMonthFromDate()
                    ) {
                        setViewVisibility(R.id.imageViewCurrentDayBackground, View.VISIBLE)
                        setViewVisibility(R.id.imageViewOtherDaysBackground, View.INVISIBLE)
                        setViewVisibility(R.id.textViewDayName,View.INVISIBLE)
                        setViewVisibility(R.id.textViewToday,View.VISIBLE)
                    }
                    setTextViewText(
                        R.id.textViewBrushingCount,
                        context.getString(
                            R.string.brushing_count,
                            brushingCount.toString(),
                            remindBrush
                        )
                    )
                    setTextViewText(R.id.textViewDayName, getDayName)
                    setProgressBar(
                        R.id.progress_in_brushing,
                        remindBrush.toInt(),
                        brushingCount,
                        false
                    )
                })
            }
        }, gridCells = GridCells.Adaptive(45.dp),
        modifier = GlanceModifier.padding(start = 20.dp, end = 20.dp, top = 40.dp).height(92.dp)
    )

}

@Composable
fun BrushNowButton(context: Context) {
    Box(
        modifier = GlanceModifier.fillMaxWidth().padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            provider = ImageProvider(R.drawable.brush_now),
            contentDescription = context.getString(
                R.string.brush_now_button
            ),
            modifier = GlanceModifier.clickable(onClick = actionRunCallback<BrushingNowClickAction>())
                .padding(top = 13.dp)
        )
        Image(
            modifier = GlanceModifier.fillMaxWidth().padding(end = 150.dp).clickable(onClick = actionRunCallback<BrushingNowClickAction>()),
            provider = ImageProvider(R.drawable.pos_brush_now),
            contentDescription = context.getString(
                R.string.brush_now_button
            ),
        )

    }
}

class ItemBrushingCountMediumView(context: Context) :
    RemoteViews(context.packageName, R.layout.item_brushing_count_medium_size)