package com.straiberry.android.widget.presentation.view.ui.layout.configuration

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import com.smarttoolfactory.slider.ColorfulIconSlider
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor
import com.straiberry.android.widget.R
import com.straiberry.android.widget.dataStore
import com.straiberry.android.widget.helper.Utility.openEmail
import com.straiberry.android.widget.helper.Utility.openLink
import com.straiberry.android.widget.presentation.view.BrushingWidget
import com.straiberry.android.widget.presentation.view.ui.actions.BrushingPerDayKey
import com.straiberry.android.widget.presentation.view.ui.theme.Blue800
import com.straiberry.android.widget.presentation.view.ui.theme.Gray
import com.straiberry.android.widget.presentation.view.ui.theme.SimBlack
import com.straiberry.android.widget.presentation.view.ui.theme.SkyBlue
import com.straiberry.android.widget.presentation.view.ui.theme.SkyBlue200
import com.straiberry.android.widget.presentation.view.ui.theme.WidgetTheme
import com.straiberry.android.widget.readValue
import com.straiberry.android.widget.storeValue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


const val BRUSHING_PER_DAY_PREF_KEY = "brushing_per_day_pref_key"

val textStyle14sp = TextStyle(fontSize = 14.sp)
val textStyle21sp = TextStyle(fontSize = 21.sp)
val textStyle16sp = TextStyle(fontSize = 16.sp)

@Composable
fun Header(appWidgetId:Int) {
    val activity = (LocalContext.current as? Activity)

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 19.dp, end = 19.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.straiberry_logo),
                contentDescription = ""
            )
            Text(
                text = stringResource(id = R.string.straiberry),
                style = textStyle14sp.copy(color = Blue800),
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1F)
            )
            Button(
                onClick = { sendOkForWidgetOnBackPressed(activity = activity!!,appWidgetId) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.ic_close), contentDescription = "")
            }
        }

        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_background_setting_title),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.padding(start = 30.dp, top = 24.dp, end = 30.dp)
            ) {
                Text(
                    text = stringResource(R.string.daily_goal_setting),
                    style = textStyle21sp.copy(color = Blue800, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .weight(1f)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_whitening), "",
                    modifier = Modifier.size(34.dp, 27.dp)
                )
            }
        }

    }

}

@Composable
fun SetBrushingPerDay(context: Context) {
    var savedBrushingPerDay: Float

    runBlocking {
        savedBrushingPerDay =
            context.dataStore.readValue(floatPreferencesKey(BRUSHING_PER_DAY_PREF_KEY), 3f).first()
    }

    var sliderPosition by remember { mutableStateOf(savedBrushingPerDay) }

    Card(
        elevation = 0.dp,
        border = BorderStroke(1.dp, SkyBlue),
        shape = RoundedCornerShape(9.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .padding(top = 50.dp, start = 24.dp, end = 24.dp)
    ) {

        Column(modifier = Modifier.padding(top = 17.dp, start = 28.dp)) {
            Text(
                text = stringResource(R.string.brushing_per_day),
                style = textStyle16sp.copy(color = SimBlack)
            )
            ColorfulIconSlider(
                value = sliderPosition,
                onValueChange = { value, _ ->
                    sliderPosition = value
                    runBlocking {
                        updateBrushingPerDay(context, value.toInt())
                        context.dataStore.storeValue(
                            floatPreferencesKey(BRUSHING_PER_DAY_PREF_KEY),
                            value
                        )
                    }
                },
                steps = 5,
                valueRange = 1f..5f,
                trackHeight = 8.dp,
                colors = MaterialSliderDefaults.materialColors(
                    inactiveTrackColor = SliderBrushColor(color = SkyBlue200),
                    activeTrackColor = SliderBrushColor(
                        color = SkyBlue200,
                    )
                ),
                borderStroke = BorderStroke(4.dp, SkyBlue200),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_circle),
                        contentDescription = "",
                        modifier = Modifier.size(42.dp, 42.dp)
                    )
                    Text(
                        text = sliderPosition.toInt().toString(),
                        style = textStyle16sp.copy(color = Color.White),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ContactUs(context: Context) {
    Card(
        elevation = 0.dp,
        border = BorderStroke(1.dp, SkyBlue),
        shape = RoundedCornerShape(9.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .padding(top = 50.dp, start = 24.dp, end = 24.dp)
    ) {
        Column(modifier = Modifier.padding(top = 18.dp, start = 28.dp, end = 13.dp)) {
            Text(
                text = stringResource(R.string.contact_us),
                style = textStyle16sp.copy(color = SimBlack)
            )

            ClickableText(
                text = AnnotatedString(stringResource(R.string.pooya_straiberry_com)),
                style = textStyle14sp.copy(color = Gray),
                modifier = Modifier.padding(top = 21.dp),
                onClick = {
                    context.getString(R.string.pooya_straiberry_com).openEmail(context)
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp), horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_web),
                    contentDescription = stringResource(
                        R.string.straiberry_website
                    ),
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable {
                            context
                                .getString(R.string.https_straiberry_com)
                                .openLink(context)
                        },
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_insta),
                    contentDescription = stringResource(
                        R.string.straiberry_instagram
                    ),
                    modifier = Modifier.clickable {
                        context.getString(R.string.straiberry_instagram_profile).openLink(context)
                    }
                )
            }
        }

    }
}


@Composable
fun Footer() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(R.string.copyright_straiberry_com_all_rights_reserved),
            style = textStyle16sp.copy(color = Gray),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 103.dp)
        )
    }

}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    WidgetTheme {
//        val context = LocalContext.current
//        Column {
//            Header()
//            SetBrushingPerDay(context = context)
//            ContactUs(context = context)
//            Footer()
//        }
//
//    }
//}


suspend fun updateBrushingPerDay(context: Context, brushingPerDay: Int) {
    GlanceAppWidgetManager(context).getGlanceIds(BrushingWidget::class.java).forEach { glanceId ->
        updateAppWidgetState(context, glanceId) { prefs ->
            prefs[BrushingPerDayKey] = brushingPerDay
        }
    }
    BrushingWidget().updateAll(context)
}
