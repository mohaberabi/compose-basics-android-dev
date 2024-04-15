package data.repository

import com.example.marsphotos.data.MarsRepository
import com.example.marsphotos.data.MarsRepositoryImpl
import fake.FakeDataSource
import fake.FakeMarsApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class MarsRepositoryTest {


    @Test
    fun `whenn get photos called retuns list of photos`() = runTest {

        val repo = MarsRepositoryImpl(FakeMarsApiService())
        assertEquals(FakeDataSource.photosList, repo.getMarsPhotos())

    }

}