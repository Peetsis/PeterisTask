package com.peteris.detail

import app.cash.turbine.test
import com.peteris.data.testdata.TestConnectionMonitor
import com.peteris.data.testdata.TestSportsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var testSportsRepository: TestSportsRepository
    private lateinit var testConnectionMonitor: TestConnectionMonitor
    private lateinit var viewModel: DetailViewModel


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        testSportsRepository = TestSportsRepository()
        testConnectionMonitor = TestConnectionMonitor()

        Dispatchers.setMain(testDispatcher)
        viewModel = DetailViewModel(
            testSportsRepository,
            1
        )
    }

    @Test
    fun `load driver data`() = runBlocking {
        viewModel.state.test {
            val latestItem = expectMostRecentItem()
            assert(latestItem.driverData!!.id == 1)
        }
    }
}