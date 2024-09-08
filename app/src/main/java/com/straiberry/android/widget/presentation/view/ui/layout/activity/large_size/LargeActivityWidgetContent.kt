package com.straiberry.android.widget.presentation.view.ui.layout.activity.large_size

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
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import com.straiberry.android.widget.R
import com.straiberry.android.widget.domain.model.BrushingModel
import com.straiberry.android.widget.helper.Utility.getDayFromDate
import com.straiberry.android.widget.helper.Utility.getDayNameFromDate
import com.straiberry.android.widget.helper.Utility.getMonthFromDate
import com.straiberry.android.widget.presentation.view.ui.actions.BrushingNowClickAction
import com.straiberry.android.widget.presentation.view.ui.layout.main.large_size.glanceBodyText18
import com.straiberry.android.widget.presentation.view.ui.layout.main.large_size.glanceBodyText21
import com.straiberry.android.widget.presentation.view.ui.theme.Blue700
import com.straiberry.android.widget.presentation.view.ui.theme.White
import java.util.Date


@Composable
fun LargeActivityWidgetContent(
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

    Row(modifier = GlanceModifier.padding(start = 30.dp, top = 33.dp)) {
        Image(
            provider = ImageProvider(R.drawable.ic_whitening),
            contentDescription = context.getString(
                R.string.whitening_icon_description
            ), modifier = GlanceModifier.size(34.dp, 27.dp)
        )
        Column {
            Text(
                modifier = GlanceModifier.padding(start = 7.dp),
                text = context.getString(R.string.daily_goal),
                style = glanceBodyText21.copy(
                    color = ColorProvider(Blue700)
                )
            )

            Row {
                Text(
                    modifier = GlanceModifier.padding(start = 7.dp),
                    text = context.getString(R.string.last_brush),
                    style = glanceBodyText18.copy(
                        color = ColorProvider(White)
                    )
                )
                Text(
                    modifier = GlanceModifier.padding(start = 7.dp),
                    text = lastBrushTime,
                    style = glanceBodyText18.copy(
                        color = ColorProvider(Blue700),
                    )
                )
            }

        }

    }
}

@Composable
fun ListOfBrushingCountForEachDay(
    context: Context,
    listOfBrushingModels: List<BrushingModel>,
    remindBrush: String
) {
    val listOfBrushingModelsArrayList = ArrayList(listOfBrushingModels)
    listOfBrushingModelsArrayList.removeIf { it.brushingDate.getDayFromDate() == Date().getDayFromDate()
            &&it.brushingDate.getMonthFromDate()== Date().getMonthFromDate()}

    val listOfLastWeekBrushingData = if (listOfBrushingModelsArrayList.size>=7)
        listOfBrushingModelsArrayList.reversed().subList(0,5)
    else
        listOfBrushingModelsArrayList.reversed()

    LazyVerticalGrid(
        content = {
            items(listOfLastWeekBrushingData.size) {
                AndroidRemoteViews(remoteViews = ItemBrushingCountLargeView(context = context).apply {
                    val getDayName = listOfLastWeekBrushingData[it].brushingDate.getDayNameFromDate()
                    val brushingCount = listOfLastWeekBrushingData[it].brushingCount

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
        }, gridCells = GridCells.Fixed(3),
        modifier = GlanceModifier.padding(start = 40.dp, end = 40.dp, top = 20.dp).height(215.dp)
    )

}

@Composable
fun BrushNowButton(context: Context) {
    Box(modifier = GlanceModifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Image(
            provider = ImageProvider(R.drawable.brush_now),
            contentDescription = context.getString(
                R.string.brush_now_button
            ),
            modifier = GlanceModifier.clickable(onClick = actionRunCallback<BrushingNowClickAction>())
                .padding(top = 13.dp)
        )
    }
}

class ItemBrushingCountLargeView(context: Context) :
    RemoteViews(context.packageName, R.layout.item_brushing_count_large_size)