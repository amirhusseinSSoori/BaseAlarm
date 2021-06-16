package com.template.basealarm.presentation.ui.alram

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.basealarm.data.repository.AlarmRepositoryImp
import com.template.basealarm.domain.entity.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(var useCase: AlarmRepositoryImp) : ViewModel() {


    val getDetailsAlarm = MutableStateFlow<StatusAlarm>(StatusAlarm.Empty)
    val getDetailsAlarmCollect: StateFlow<StatusAlarm> = getDetailsAlarm



    suspend  fun insertToDbAlarm(alarm: Alarm){
        useCase.insertToAlarm(alarm)
    }
    fun showDetailsAlarm() {
       viewModelScope.launch {
           useCase.getAllDetailsFromAlarm().collect {
             getDetailsAlarm.value = StatusAlarm.Show(it)
         }
       }
    }




    sealed class StatusAlarm() {
        class Show(val state: List<Alarm>) : StatusAlarm()
        object Empty : StatusAlarm()
    }


}