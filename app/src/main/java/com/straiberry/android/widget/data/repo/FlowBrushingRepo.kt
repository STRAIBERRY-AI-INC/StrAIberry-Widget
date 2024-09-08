package com.straiberry.android.widget.data.repo

import com.straiberry.android.widget.data.db.BrushingDao
import com.straiberry.android.widget.domain.model.BrushingModel
import com.straiberry.android.widget.domain.repo.BrushingRepo
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlowBrushingRepo @Inject constructor(private val brushingDao: BrushingDao) : BrushingRepo {
    override suspend fun getAllBrushing(): List<BrushingModel> = brushingDao.getAllBrushing()

    override suspend fun addNewBrushing(brushingModel: BrushingModel) {
        brushingDao.addBrushing(brushingModel = brushingModel)
    }

    override suspend fun updateBrushing(brushingCount: Int, brushingId: Int,brushingDate:Date) {
        brushingDao.updateBrushing(brushingCount = brushingCount, brushingId = brushingId, brushingDate = brushingDate)
    }
}