package com.straiberry.android.widget.presentation.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.straiberry.android.widget.helper.Utility.convertToStringJson
import com.straiberry.android.widget.domain.usecase.GetAllBrushingUseCase
import com.straiberry.android.widget.presentation.view.ui.actions.getListOfAllBrushing
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class GetAllBrushingWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val getAllBrushingUseCase: GetAllBrushingUseCase
):CoroutineWorker(context,workerParameters){
    override suspend fun doWork(): Result {
        return try {
            getListOfAllBrushing(context,getAllBrushingUseCase.execute().convertToStringJson())
            Result.success()
        }catch (e:Exception){
            Result.failure()
        }
    }
}