package com.techdevlp.minibotai.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class StoredDataPreference(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore("minibotais_storedata")
        private val IS_ON_BOARDING_FINISHED = stringPreferencesKey("is_on_boarding_finished")

    }

    suspend fun getOnBoardingToken(): Boolean = context.dataStore.data.map { preferences ->
        preferences[IS_ON_BOARDING_FINISHED]?.toBoolean()?:false
    }.first()

    suspend fun saveOnBoardingToken(token: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_ON_BOARDING_FINISHED] = token.toString()
        }
    }
}