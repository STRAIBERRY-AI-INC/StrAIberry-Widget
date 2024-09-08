package com.straiberry.android.widget.domain.usecase

import com.straiberry.android.widget.data.db.BrushingDao
import com.straiberry.android.widget.domain.model.BrushingModel
import javax.inject.Inject

class AddNewBrushingUseCase @Inject constructor(private  val brushingDao: BrushingDao) {
    fun execute(brushingModel: BrushingModel) = brushingDao.addBrushing(brushingModel)
}