package com.template.basealarm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.template.basealarm.data.db.entity.AlarmEntity

@Database(
    entities = [AlarmEntity::class],
    version  =  1
)
abstract class DataBase : RoomDatabase()  {

    abstract fun getMyDao(): MainDao
}