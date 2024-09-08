package com.straiberry.android.widget.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.straiberry.android.widget.R
import com.straiberry.android.widget.domain.model.BrushingModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object Utility {

    private const val TIME_FORMAT = "%02d:%02d"
    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS

    fun Long.formatTime(): String = String.format(
        TIME_FORMAT,
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )

    fun Date.getDayFromDate(): String {
        var day: String
        Calendar.getInstance().apply {
            time = this@getDayFromDate
            day = SimpleDateFormat("d", Locale.ENGLISH).apply {
                timeZone = TimeZone.getDefault()
            }.format(time)
        }
        return day
    }

    fun Date.getDayNameFromDate(): String {
        var day: String
        Calendar.getInstance().apply {
            time = this@getDayNameFromDate
            day = SimpleDateFormat("EEE", Locale.ENGLISH).apply {
                timeZone = TimeZone.getDefault()
            }.format(time)
        }
        return day
    }

    fun Date.getDayNamePlusDayOfMonthAndMonthNameFromDate(): String {
        var day: String
        Calendar.getInstance().apply {
            time = this@getDayNamePlusDayOfMonthAndMonthNameFromDate
            day = SimpleDateFormat("EEE dd MMM", Locale.ENGLISH).apply {
                timeZone = TimeZone.getDefault()
            }.format(time)
        }
        return day
    }

    fun Date.getYearFromDate(): String {
        var year: String
        Calendar.getInstance().apply {
            time = this@getYearFromDate
            year = get(Calendar.YEAR).toString()
        }
        return year
    }


    fun Date.getMonthFromDate(): String {
        var month: String
        Calendar.getInstance().apply {
            time = this@getMonthFromDate
            month = SimpleDateFormat("MMM", Locale.ENGLISH).apply {
                timeZone = TimeZone.getDefault()
            }.format(time)
        }
        return month
    }

    fun String.convertToBrushingModel(): List<BrushingModel> {
        return Gson().fromJson(this, Array<BrushingModel>::class.java).asList()
    }

    fun List<BrushingModel>.convertToStringJson(): String {
        return Gson().toJson(this)
    }

    fun List<BrushingModel>.getCurrentDayBrushingCount(): Int {
        return this.firstOrNull {
            it.brushingDate.getDayFromDate() == Date().getDayFromDate() &&
                    it.brushingDate.getYearFromDate() == Date().getYearFromDate() &&
                    it.brushingDate.getMonthFromDate() == Date().getMonthFromDate()
        }?.brushingCount
            ?: 0
    }

    fun List<BrushingModel>.getLastBrush(): String {
        val lastBrushingDate = this.last().brushingDate.time
        val currentDate = Date().time
        val dif = currentDate - lastBrushingDate

        return if (lastBrushingDate == 0L || currentDate < lastBrushingDate)
            ""
        else if (dif < MINUTE_MILLIS)
            "just now"
        else if (dif < 50 * MINUTE_MILLIS)
            "${(dif / MINUTE_MILLIS)} m ago"
        else if (dif < 90 * MINUTE_MILLIS)
            "an hour ago"
        else if (dif < 24 * HOUR_MILLIS)
            "${dif / HOUR_MILLIS} h ago"
        else if (dif < 48 * HOUR_MILLIS)
            "yesterday"
        else
            "${dif / DAY_MILLIS} day ago"

    }

    fun String.openLink(context: Context){
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(this))
        ContextCompat.startActivity(context, browserIntent, null)
    }

    fun String.openEmail(context: Context){
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("mailto:$this")
            )
            ContextCompat.startActivity(context, intent, null)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                context.getString(R.string.sorry_you_don_t_have_any_mail_app),
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }
}