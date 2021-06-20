package com.template.basealarm.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarmEntity")
data class AlarmEntity(
    var time: String? = null,
    var date: String? = null,
    var alarmBefore: Boolean? = null,
    var alarmStatus:String?=null,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)