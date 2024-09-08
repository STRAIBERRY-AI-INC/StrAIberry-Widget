package com.straiberry.android.widget.presentation.view.ui.layout.activity.small_size

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
import androidx.glance.appwidget.lazy.LazyColumn
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
import com.straiberry.android.widget.helper.Utility.getDayNamePlusDayOfMonthAndMonthNameFromDate
import com.straiberry.android.widget.presentation.view.ui.actions.BrushingNowClickAction
import com.straiberry.android.widget.presentation.view.ui.layout.main.medium_size.glanceBodyText14
import com.straiberry.android.widget.presentation.view.ui.theme.Blue700


@Composable
fun SmallActivityWidgetContent(
    modifier: GlanceModifier,
    remindBrush: String,
    listOfBrushingModels: List<BrushingModel>,
) {

    Column(modifier = modifier) {
        val context = LocalContext.current
        MainTitle(context = context)
        ListOfBrushingCountForEachDay(
            context = context,
            listOfBrushingModels = listOfBrushingModels,
            remindBrush = remindBrush
        )
        BrushingButton(context = context)
    }
}

@Composable
fun ListOfBrushingCountForEachDay(
    context: Context,
    listOfBrushingModels: List<BrushingModel>,
    remindBrush: String
) {
    val listOfLastWeekBrushingData = if (listOfBrushingModels.size>=7)
        listOfBrushingModels.reversed().subList(0,6)
    else
        listOfBrushingModels.reversed()

    LazyColumn(
        content = {
            items(listOfLastWeekBrushingData.size) {
                AndroidRemoteViews(remoteViews = ItemBrushingCountSmallView(context = context).apply {
                    val getDayName =
                        listOfLastWeekBrushingData[it].brushingDate.getDayNamePlusDayOfMonthAndMonthNameFromDate()
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
        },
        modifier = GlanceModifier.padding(start = 20.dp, end = 20.dp, top = 10.dp).height(115.dp)
    )

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
            modifier = GlanceModifier.padding(start = 7.dp),
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
                .padding(top = 8.dp)
        )
    }
}

class ItemBrushingCountSmallView(context: Context) :
    RemoteViews(context.packageName, R.layout.item_brushing_count_small_size)
