package com.template.basealarm.data.db.base

import androidx.room.Insert
import androidx.room.*


interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T)
    @Delete
    fun delete(obj: T)
}
