package com.skysam.hchirinos.circulalo.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object Preferences {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.PREFERENCES)
    private val sharedPref = Circulalo.Circulalo.getContext().getSharedPreferences(
        Constants.PREFERENCES, Context.MODE_PRIVATE)

    private val PREFERENCE_NOTIFICATION = booleanPreferencesKey(Constants.PREFERENCES_NOTIFICATION)

    fun getNotificationStatus(): Flow<Boolean> {
        return Circulalo.Circulalo.getContext().dataStore.data
            .map {
                it[PREFERENCE_NOTIFICATION] ?: true
            }
    }

    suspend fun changeNotificationStatus(status: Boolean) {
        Circulalo.Circulalo.getContext().dataStore.edit {
            it[PREFERENCE_NOTIFICATION] = status
        }
    }
}