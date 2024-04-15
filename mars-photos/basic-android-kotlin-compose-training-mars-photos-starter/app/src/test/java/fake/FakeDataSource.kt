package fake

import com.example.marsphotos.model.Mars

object FakeDataSource {

    const val idOne = "img1"
    const val idTwo = "img2"
    const val imgOne = "url.1"
    const val imgTwo = "url.2"
    val photosList = listOf(
        Mars(
            id = idOne,
            image = imgOne
        ),
        Mars(
            id = idTwo,
            image = imgTwo
        )
    )
}