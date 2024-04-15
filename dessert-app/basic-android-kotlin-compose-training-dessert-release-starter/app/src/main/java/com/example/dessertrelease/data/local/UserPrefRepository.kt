package com.example.dessertrelease.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.sql.DataSource

class UserPrefRepository(
    private val datastore: DataStore<Preferences>
) {

    companion object {
        val isLinear = booleanPreferencesKey("isLinear")
        const val TAG = "UserPreferencesRepo"

    }

    suspend fun saveLayoutPref(isLinearLayoutOut: Boolean) {
        datastore.edit { pref ->
            pref[isLinear] = isLinearLayoutOut
        }
    }

    val isLinearLayout: Flow<Boolean> = datastore.data.catch {
        if (it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { pref ->
        pref[isLinear] ?: true
    }

}