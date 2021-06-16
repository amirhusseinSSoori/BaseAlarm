package com.template.basealarm.data.di

import android.content.Context
import com.template.basealarm.data.db.DataBase
import androidx.room.Room
import com.template.basealarm.common.Constance.DATABASE_NAME
import com.template.basealarm.data.db.MainDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMyDb(
        @ApplicationContext context: Context
    ): DataBase {
        return Room
            .databaseBuilder(
                context,
                DataBase::class.java,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }



    @Provides
    fun provideMyDAO(dataBase: DataBase): MainDao {
        return dataBase.getMyDao()
    }
}