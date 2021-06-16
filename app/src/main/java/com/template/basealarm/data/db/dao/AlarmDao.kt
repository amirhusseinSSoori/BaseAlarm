package com.template.basealarm.data.db.dao

import androidx.room.*
import com.template.basealarm.data.db.base.BaseDao
import com.template.basealarm.data.db.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AlarmDao : BaseDao<AlarmEntity> {
    @Query("SELECT * FROM alarmEntity")
    abstract fun getAllData(): Flow<List<AlarmEntity>>

}