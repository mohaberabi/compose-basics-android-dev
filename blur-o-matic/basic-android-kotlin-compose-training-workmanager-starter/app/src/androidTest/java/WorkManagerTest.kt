import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.workDataOf
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.workers.BlurWorker
import com.example.bluromatic.workers.CleanupWorker
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WorkManagerTest {


    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    private val pair =
        KEY_IMAGE_URI to "android.resource://com.example.bluromatic/drawable/android_cupcake"


    @Test
    fun bluerWorkerTest() {

        val worker = TestListenableWorkerBuilder<BlurWorker>(context).setInputData(
            workDataOf(pair)
        ).build()

        runBlocking {
            val res = worker.doWork()
            assertTrue(res is ListenableWorker.Result.Success)
            assertTrue(res.outputData.keyValueMap.containsKey(KEY_IMAGE_URI))


        }
    }

    @Test
    fun cleanUpWorkerTest() {

        val worker =
            TestListenableWorkerBuilder<CleanupWorker>(context)
                .build()
        runBlocking {
            val result = worker.doWork()
            assertTrue(result is ListenableWorker.Result.Success)

        }
    }
}