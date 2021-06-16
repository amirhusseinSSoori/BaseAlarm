package com.template.basealarm.data.mapper


import com.arad.data.mapper.base.EntityMapper
import com.template.basealarm.data.db.entity.AlarmEntity
import com.template.basealarm.domain.entity.Alarm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmMapper @Inject constructor() : EntityMapper<Alarm, AlarmEntity> {
    override fun mapFromEntity(entity: Alarm): AlarmEntity {
        return AlarmEntity(
            time = entity.time,
            date = entity.date,
            alarmBefore = entity.alarmBefore,
            alarmId=entity.alarmId,
            id = entity.id
        )
    }
    override fun mapToEntity(domainModel: AlarmEntity): Alarm {
        return Alarm(
            time = domainModel.time,
            date = domainModel.date,
            alarmBefore = domainModel.alarmBefore,
            alarmId=domainModel.alarmId,
            id = domainModel.id
        )
    }
    override fun mapFromEntityList(entities: List<Alarm>): List<AlarmEntity> {
        return entities.map { mapFromEntity(it) }
    }
    override fun mapToEntityList(domains: List<AlarmEntity>): List<Alarm> {
        return domains.map { mapToEntity(it) }
    }



    }


