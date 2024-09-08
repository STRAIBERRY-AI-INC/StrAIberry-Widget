package com.straiberry.android.widget.domain.usecase

import com.straiberry.android.widget.data.db.BrushingDao
import java.util.Date
import javax.inject.Inject

class UpdateBrushingCountUseCase @Inject constructor(private val brushingDao: BrushingDao) {
    fun execute(brushingCount: Int, brushingId: Int, brushingDate: Date) =
        brushingDao.updateBrushing(brushingCount, brushingId, brushingDate)
}