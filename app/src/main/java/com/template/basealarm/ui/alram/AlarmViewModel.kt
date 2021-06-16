package com.template.basealarm.ui.alram

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.basealarm.data.db.entity.AlarmEntity
import com.template.basealarm.data.repository.AlarmRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(var repository: AlarmRepositoryImp) : ViewModel() {


    val getDetailsAlarm = MutableStateFlow<StatusAlarm>(StatusAlarm.Empty)
    val getDetailsAlarmCollect: StateFlow<StatusAlarm> = getDetailsAlarm



    suspend  fun insertToDbAlarm(alarmEntity: AlarmEntity){
        repository.insertToAlarm(alarmEntity)
    }


    fun showDetailsAlarm() {
       viewModelScope.launch {
         repository.getDetailsFromDb().collect {
             getDetailsAlarm.value = StatusAlarm.Show(it)
         }
       }
    }


    sealed class StatusAlarm() {
        class Show(val state: List<AlarmEntity>) : StatusAlarm()
        object Empty : StatusAlarm()
    }


}