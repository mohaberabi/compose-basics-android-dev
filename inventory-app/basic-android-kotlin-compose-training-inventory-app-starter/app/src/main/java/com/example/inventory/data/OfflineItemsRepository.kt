package com.example.inventory.data

import com.example.inventory.data.db.ItemDao
import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val itemsDao: ItemDao) : ItemsRepository {
    override fun getAllItemsStream(): Flow<List<Item>> = itemsDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Item?> = itemsDao.getItem(id)

    override suspend fun insertItem(item: Item) = itemsDao.addItem(item = item)


    override suspend fun deleteItem(item: Item) = itemsDao.deleteItem(item = item)
    override suspend fun updateItem(item: Item) = itemsDao.updateItem(item)
}
