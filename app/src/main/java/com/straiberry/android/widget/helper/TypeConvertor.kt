package com.straiberry.android.widget.helper

import androidx.room.TypeConverter
import java.util.Date

class TypeConvertor {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}