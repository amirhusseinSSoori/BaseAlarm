package com.template.basealarm.domain.entity


data class Alarm(
    var time: String? = null,
    var date: String? = null,
    var alarmBefore: Boolean? = null,
    var alarmed:Boolean?=null,
    val id: Int? = null
)