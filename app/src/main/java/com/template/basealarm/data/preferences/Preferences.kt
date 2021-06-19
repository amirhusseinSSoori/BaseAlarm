package com.template.basealarm.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Preferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val SORT_ORDER = intPreferencesKey("sort_alarmId") }








    // get Details for new AlarmId
    val insertAlarmIdfPreferencesFlow: Flow<AlarmIdPreferences> = dataStore.data
        .map { preferences ->
            val alarmId = preferences[PreferencesKeys.SORT_ORDER] ?: 0
            AlarmIdPreferences(alarmId)

        }

    suspend fun updateAlarmId(alarmId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SORT_ORDER] = alarmId
        }
    }




}