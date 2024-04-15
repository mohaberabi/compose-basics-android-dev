package ui

import com.example.marsphotos.ui.screens.MarsState
import com.example.marsphotos.ui.screens.MarsViewModel
import fake.FakeDataSource
import fake.FakeMarsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import rules.TestDispatcherRule

class MarsViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun `whenn reposistory returns the list viewmodel should emit done `() = runTest {

        val viewModel = MarsViewModel(FakeMarsRepository())

        assertEquals(MarsState.Success(FakeDataSource.photosList), viewModel.state)
    }

}