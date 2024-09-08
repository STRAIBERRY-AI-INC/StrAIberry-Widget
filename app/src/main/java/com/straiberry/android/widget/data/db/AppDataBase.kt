package com.straiberry.android.widget.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.straiberry.android.widget.di.ApplicationScope
import com.straiberry.android.widget.domain.model.BrushingModel
import com.straiberry.android.widget.helper.TypeConvertor
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [BrushingModel::class],
    version = 1
)
@TypeConverters(TypeConvertor::class)
abstract class AppDataBase :RoomDatabase(){
    abstract fun brushingDao():BrushingDao

    class Callback @Inject constructor(
        private val database: Provider<AppDataBase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}