package com.straiberry.android.widget.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.straiberry.android.widget.domain.model.BrushingModel
import java.util.Date

@Dao
interface BrushingDao {

    @Query("SELECT * from brushing")
    fun getAllBrushing(): List<BrushingModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBrushing(brushingModel: BrushingModel)

    @Query("UPDATE brushing SET brushingCount=:brushingCount , brushingDate=:brushingDate WHERE id==:brushingId")
    fun updateBrushing(brushingCount:Int,brushingId:Int,brushingDate:Date)
}