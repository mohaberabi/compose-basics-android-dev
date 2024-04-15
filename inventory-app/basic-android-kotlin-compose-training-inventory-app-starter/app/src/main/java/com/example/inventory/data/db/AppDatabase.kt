package com.example.inventory.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.inventory.data.Item


@Database(
    entities = [Item::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {


    companion object {
        @Volatile
        private var Instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "item.db").build()
                    .also {
                        Instance = it
                    }
            }
        }

    }

    abstract val itemsDao: ItemDao
}