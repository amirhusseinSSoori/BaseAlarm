package com.template.basealarm.data.source

import com.template.basealarm.data.db.dao.AlarmDao
import com.template.basealarm.data.mapper.AlarmMapper
import com.template.basealarm.domain.entity.Alarm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class LocalDataBase @Inject constructor(private var daoAlarm: AlarmDao, var alarmMapper: AlarmMapper) {
    suspend fun insertToAlarm(alarm: Alarm) {
        daoAlarm.insert(alarmMapper.mapFromEntity(alarm))
    }

    fun getAllDetailsFromDao(): Flow<List<Alarm>> {
        return daoAlarm.getAllData().map { alarmMapper.mapToEntityList(it) }
    }
}