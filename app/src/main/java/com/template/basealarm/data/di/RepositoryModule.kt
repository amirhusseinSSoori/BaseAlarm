package com.template.basealarm.data.di

import com.template.basealarm.data.repository.AlarmRepositoryImp
import com.template.basealarm.data.source.LocalDataBase
import com.template.basealarm.domain.repository.AlarmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAlarmRepository(local: LocalDataBase): AlarmRepository = AlarmRepositoryImp(local)
}