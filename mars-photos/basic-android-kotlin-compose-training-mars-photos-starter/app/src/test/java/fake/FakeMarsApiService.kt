package fake

import com.example.marsphotos.model.Mars
import com.example.marsphotos.network.MarsService

class FakeMarsApiService : MarsService {
    override suspend fun getPhotos(): List<Mars> {
        return FakeDataSource.photosList

    }
}