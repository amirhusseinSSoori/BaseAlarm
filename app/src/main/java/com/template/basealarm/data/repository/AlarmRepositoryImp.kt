package com.template.basealarm.data.repository

import com.template.basealarm.data.db.entity.AlarmEntity
import com.template.basealarm.data.source.LocalDataBase
import com.template.basealarm.domain.entity.Alarm
import com.template.basealarm.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImp @Inject constructor(var local:LocalDataBase):AlarmRepository {
    override suspend fun insertToAlarm(alarm: Alarm) {
        local.insertToAlarm(alarm)
    }
    override fun getAllDetailsFromAlarm(): Flow<List<Alarm>> {
      return local.getAllDetailsFromDao()
    }
}