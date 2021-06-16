package com.template.basealarm.data.source

import com.template.basealarm.data.db.MainDao
import com.template.basealarm.data.db.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocalDataBase @Inject constructor(var db:MainDao) {





    suspend fun insertToAlarm(alarmEntity: AlarmEntity){
        db.insert(alarmEntity)
    }

    fun getAllDetails(): Flow<List<AlarmEntity>> {
       return db.getAllData()
    }



}