package com.straiberry.android.widget.presentation.workers

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.straiberry.android.widget.domain.model.BrushingModel
import com.straiberry.android.widget.domain.usecase.AddNewBrushingUseCase
import com.straiberry.android.widget.domain.usecase.GetAllBrushingUseCase
import com.straiberry.android.widget.domain.usecase.UpdateBrushingCountUseCase
import com.straiberry.android.widget.helper.Utility.getDayFromDate
import com.straiberry.android.widget.helper.Utility.getMonthFromDate
import com.straiberry.android.widget.helper.Utility.getYearFromDate
import com.straiberry.android.widget.presentation.view.BrushingWidget
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.lang.Exception
import java.util.Date


/**
 * The class takes in several parameters using the `@AssistedInject` annotation, including a `Context`, `WorkerParameters`,
 * and three use case classes for adding new brushing data, updating brushing count, and getting all brushing data.
 * The `doWork()` function is overridden, which executes the work that needs to be done by the worker.
 * The function tries to get all the brushing data and then looks for brushing data belonging to the current day.
 * If found, it updates the brushing count for that day using the `updateBrushingCountUseCase` instance.
 * If no brushing data for the current day is found, it adds new brushing data using the `addNewBrushingUseCase` instance.
 */
@HiltWorker
class AddNewBrushingWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val addNewBrushingUseCase: AddNewBrushingUseCase,
    private val updateBrushingCountUseCase: UpdateBrushingCountUseCase,
    private val getAllBrushingUseCase: GetAllBrushingUseCase
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {

            val listOfAllBrushing = getAllBrushingUseCase.execute()
            var findCurrentDayOfBrushingByListIndex = -1
            var findCurrentDayOfBrushingById = -1
            var countOfBrushingInCurrentDay: Int

            listOfAllBrushing.forEachIndexed { index, brushingModel ->
                if (brushingModel.brushingDate.getYearFromDate() == Date().getYearFromDate() &&
                    brushingModel.brushingDate.getMonthFromDate() == Date().getMonthFromDate() &&
                    brushingModel.brushingDate.getDayFromDate() == Date().getDayFromDate()
                ) {
                    findCurrentDayOfBrushingByListIndex = index
                    findCurrentDayOfBrushingById = brushingModel.id
                }
            }

            if (findCurrentDayOfBrushingByListIndex != -1) {
                countOfBrushingInCurrentDay =
                    listOfAllBrushing[findCurrentDayOfBrushingByListIndex].brushingCount

                countOfBrushingInCurrentDay++

                updateBrushingCountUseCase.execute(
                    countOfBrushingInCurrentDay,
                    findCurrentDayOfBrushingById,
                    Date()
                )
            } else
                addNewBrushingUseCase.execute(
                    BrushingModel(brushingDate = Date())
                )

            BrushingWidget().updateAll(context)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}