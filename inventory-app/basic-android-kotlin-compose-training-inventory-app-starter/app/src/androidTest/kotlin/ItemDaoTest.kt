import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inventory.data.Item
import com.example.inventory.data.db.AppDatabase
import com.example.inventory.data.db.ItemDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ItemDaoTest {
    private var item1 = Item(1, "Apples", 10.0, 20)
    private var item2 = Item(2, "Bananas", 15.0, 97)
    private lateinit var itemDao: ItemDao
    private lateinit var inventoryDatabase: AppDatabase


    @Before
    fun createDatabase() {
        val context: Context = ApplicationProvider.getApplicationContext()
        inventoryDatabase =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        itemDao = inventoryDatabase.itemsDao
    }

    @After
    fun closeDb() {
        inventoryDatabase.close()
    }

    private suspend fun addOneItemToDb() {
        itemDao.addItem(item1)
    }

    private suspend fun addTwoItemsToDb() {
        itemDao.addItem(item1)
        itemDao.addItem(item2)
    }


    @Test
    @Throws(Exception::class)
    fun addsItmToDb() = runBlocking {
        addOneItemToDb()
        val items = itemDao.getAllItems().first()
        assertEquals(items[0].id, item1.id)
    }

    @Test
    fun getAllItemsReturnsTheItems() = runBlocking {

        addTwoItemsToDb()
        val items = itemDao.getAllItems().first()
        assertEquals(items[0].id, item1.id)
        assertEquals(items[1].id, item2.id)

    }

    @Test
    fun daoUpdatesItem() = runBlocking {
        addTwoItemsToDb()
        itemDao.updateItem(Item(1, "Apples", 15.0, 25))
        itemDao.updateItem(Item(2, "Bananas", 5.0, 50))
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], Item(1, "Apples", 15.0, 25))
        assertEquals(allItems[1], Item(2, "Bananas", 5.0, 50))
    }

    @Test
    fun daoDeletesItem() = runBlocking {
        addTwoItemsToDb()
        itemDao.deleteItem(item1)
        itemDao.deleteItem(item2)
        val items = itemDao.getAllItems().first()
        assert(items.isEmpty())
    }
}