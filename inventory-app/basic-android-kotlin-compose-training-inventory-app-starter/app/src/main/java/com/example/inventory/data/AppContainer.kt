package com.example.inventory.data

import android.content.Context
import com.example.inventory.data.db.AppDatabase

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
}


class AppDataContainer(private val context: Context) : AppContainer {

    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(AppDatabase.getDatabase(context).itemsDao)
    }
}
