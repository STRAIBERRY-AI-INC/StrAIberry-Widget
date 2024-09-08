package com.straiberry.android.widget.presentation.view.ui.layout.configuration

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.straiberry.android.widget.presentation.view.BrushingWidget
import com.straiberry.android.widget.presentation.view.ui.theme.White200
import com.straiberry.android.widget.presentation.view.ui.theme.WidgetTheme

class ConfigureActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        setContent {
            WidgetTheme {
                val context = LocalContext.current
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = White200
                ) {
                    Column {
                        Header(appWidgetId)
                        SetBrushingPerDay(context = context)
                        ContactUs(context = context)
                        Footer()
                    }
                }
            }
        }


        onBackPressedDispatcher.addCallback(this) {
            sendOkForWidgetOnBackPressed(this@ConfigureActivity, appWidgetId = appWidgetId)
        }


    }
}

fun sendOkForWidgetOnBackPressed(activity: Activity,appWidgetId:Int) {

    val resultValue = Intent()
    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    activity.setResult(ComponentActivity.RESULT_OK, resultValue)
    activity.finish()
}

