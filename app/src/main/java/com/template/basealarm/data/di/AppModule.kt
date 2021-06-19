package com.template.basealarm.data.di

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import com.template.basealarm.data.db.DataBase
import androidx.room.Room
import com.template.basealarm.common.Constance.DATABASE_NAME
import com.template.basealarm.data.db.dao.AlarmDao
import com.template.basealarm.data.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideMyDb(
        @ApplicationContext context: Context
    ): DataBase {
        return Room
            .databaseBuilder(
                context,
                DataBase::class.java,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    fun provideMyDAO(dataBase: DataBase): AlarmDao {
        return dataBase.getMyDao()
    }


    @Singleton
    @Provides
    fun provideUserPreferencesRepository(@ApplicationContext context: Context): Preferences {
        return Preferences(dataStore = context.dataStore)
    }
}


private const val USER_PREFERENCES_NAME = "user_preferences"
private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        // Since we're migrating from SharedPreferences, add a migration based on the
        // SharedPreferences name
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)