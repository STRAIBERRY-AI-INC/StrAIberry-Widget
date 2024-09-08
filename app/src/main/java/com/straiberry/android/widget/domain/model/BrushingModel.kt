package com.straiberry.android.widget.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.straiberry.android.widget.helper.Utility.getDayFromDate
import java.util.Date

@Entity(tableName = "brushing")
data class BrushingModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val brushingDate: Date,
    val brushingCount:Int = 1
)