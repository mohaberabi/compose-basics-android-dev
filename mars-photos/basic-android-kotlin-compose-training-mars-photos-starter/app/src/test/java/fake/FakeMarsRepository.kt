package fake

import com.example.marsphotos.data.MarsRepository
import com.example.marsphotos.model.Mars

class FakeMarsRepository : MarsRepository {
    override suspend fun getMarsPhotos(): List<Mars> {
        return FakeDataSource.photosList
    }
}