package com.template.basealarm.data.repository

import com.template.basealarm.data.db.entity.AlarmEntity
import com.template.basealarm.data.source.LocalDataBase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImp @Inject constructor(var local:LocalDataBase) {



    fun getDetailsFromDb(): Flow<List<AlarmEntity>> {
        return  local.getAllDetails()

    }


    suspend fun insertToAlarm(alarmEntity: AlarmEntity) {
        local.insertToAlarm(alarmEntity)

    }
}