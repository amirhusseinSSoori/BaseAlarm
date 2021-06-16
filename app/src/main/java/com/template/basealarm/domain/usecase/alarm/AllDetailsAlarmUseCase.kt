package com.template.basealarm.domain.usecase.alarm

import com.arad.domain.usecase.base.UseCaseWithParamsImmediate
import com.template.basealarm.domain.entity.Alarm
import com.template.basealarm.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AllDetailsAlarmUseCase @Inject constructor(var alarmRepository: AlarmRepository)  : UseCaseWithParamsImmediate<Boolean, Flow<List<Alarm>>>() {
    override fun buildUseCaseImmediate(params: Boolean): Flow<List<Alarm>> {
        return alarmRepository.getAllDetailsFromAlarm()
    }
}