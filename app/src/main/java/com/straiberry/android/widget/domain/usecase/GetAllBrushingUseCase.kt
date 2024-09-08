package com.straiberry.android.widget.domain.usecase

import com.straiberry.android.widget.data.db.BrushingDao
import javax.inject.Inject

class GetAllBrushingUseCase @Inject constructor(private val brushingDao: BrushingDao) {
    fun execute() = brushingDao.getAllBrushing()
}