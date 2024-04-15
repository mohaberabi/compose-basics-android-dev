package com.example.dessertrelease

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.dessertrelease.data.local.UserPrefRepository

private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"


class DessertApp : Application() {
    lateinit var userPrefRepository: UserPrefRepository
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = LAYOUT_PREFERENCE_NAME
    )

    override fun onCreate() {
        super.onCreate()

        userPrefRepository = UserPrefRepository(dataStore)
    }
}


