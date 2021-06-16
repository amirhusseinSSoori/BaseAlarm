package com.template.basealarm.domain.repository


import com.template.basealarm.domain.entity.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    suspend fun insertToAlarm(alarm: Alarm)
    fun getAllDetailsFromAlarm(): Flow<List<Alarm>>
}