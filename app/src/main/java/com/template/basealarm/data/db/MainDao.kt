package com.template.basealarm.data.db

import androidx.room.*
import com.template.basealarm.data.db.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarmEntity: AlarmEntity)

    @Query("SELECT * FROM alarmEntity")
    fun getAllData(): Flow<List<AlarmEntity>>

}