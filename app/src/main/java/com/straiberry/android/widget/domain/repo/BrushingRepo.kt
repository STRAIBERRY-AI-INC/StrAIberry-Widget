package com.straiberry.android.widget.domain.repo

import com.straiberry.android.widget.domain.model.BrushingModel
import java.util.Date

interface BrushingRepo {
    suspend fun getAllBrushing():List<BrushingModel>

    suspend fun addNewBrushing(brushingModel: BrushingModel)

    suspend fun updateBrushing(brushingCount:Int,brushingId:Int,brushingDate:Date)
}