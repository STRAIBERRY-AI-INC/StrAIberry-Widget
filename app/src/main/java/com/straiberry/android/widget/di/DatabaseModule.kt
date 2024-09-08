package com.straiberry.android.widget.di

import android.app.Application
import androidx.room.Room
import com.straiberry.android.widget.data.db.AppDataBase
import com.straiberry.android.widget.data.db.BrushingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: AppDataBase.Callback): AppDataBase{
        return Room.databaseBuilder(application, AppDataBase::class.java, "brushingWidget")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideArticleDao(db: AppDataBase): BrushingDao{
        return db.brushingDao()
    }
}