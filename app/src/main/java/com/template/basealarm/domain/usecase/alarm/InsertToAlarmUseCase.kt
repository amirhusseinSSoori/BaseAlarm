package com.template.basealarm.domain.usecase.alarm

import com.template.basealarm.domain.entity.Alarm
import com.template.basealarm.domain.repository.AlarmRepository
import com.template.basealarm.domain.usecase.base.UseCase
import javax.inject.Inject

class InsertToAlarmUseCase @Inject constructor(var alarm:AlarmRepository) : UseCase<Alarm, Unit>(){
    override suspend fun execute(params: Alarm?) {
        alarm.insertToAlarm(params!!)
    } }