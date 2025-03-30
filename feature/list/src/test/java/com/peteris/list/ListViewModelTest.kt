package com.peteris.list

import app.cash.turbine.test
import com.peteris.data.testdata.TestConnectionMonitor
import com.peteris.data.testdata.TestSportsRepository
import com.peteris.list.state.ListAction
import com.peteris.list.state.RankingSeason
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ListViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher  = UnconfinedTestDispatcher()

    private lateinit var testSportsRepository: TestSportsRepository
    private lateinit var testConnectionMonitor: TestConnectionMonitor
    private lateinit var viewModel: ListViewModel


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        testSportsRepository = TestSportsRepository()
        testConnectionMonitor = TestConnectionMonitor()

        Dispatchers.setMain(testDispatcher)
        viewModel = ListViewModel(
            testSportsRepository,
            testConnectionMonitor
        )
    }

    @Test
    fun `Load list from DB if API returns error`() = runBlocking {
        testSportsRepository.testRemoteDrivers.value = Result.failure(IllegalStateException())
        viewModel.onAction(ListAction.LoadList)

        viewModel.state.test {
            val latestItem = expectMostRecentItem()
            assertEquals(latestItem.itemList, testSportsRepository.testDrivers.value)
        }
    }

    @Test
    fun `load list from API`() = runBlocking {
        viewModel.state.test {
            val latestItem = expectMostRecentItem()
            assertEquals(latestItem.itemList, testSportsRepository.testRemoteDrivers.value.getOrNull())
        }
    }

    @Test
    fun `refresh action`() = runBlocking {
        viewModel.state.test {
            val latestItem = expectMostRecentItem()
            assertEquals(latestItem.itemList, testSportsRepository.testRemoteDrivers.value.getOrNull())

            viewModel.onAction(ListAction.Refresh)
            assert(awaitItem().isRefreshing)
            val lastItem = expectMostRecentItem()
            assert(!lastItem.isRefreshing)
        }
    }

    @Test
    fun `get ranking season from DB first if its not empty`() = runBlocking {
        viewModel.state.test {
            val latestItem = expectMostRecentItem()
            assertEquals(latestItem.rankingSeason.season, latestItem.itemList.first().season)
            assertEquals(latestItem.rankingSeason.season, 2022)
        }
    }

    @Test
    fun `use state ranking season if response and DB empty`() = runBlocking {
        testSportsRepository.testDrivers.value = emptyList()
        testSportsRepository.testRemoteDrivers.value = Result.failure(IllegalStateException())
        viewModel.state.test {
            val latestItem = expectMostRecentItem()
            assertEquals(latestItem.rankingSeason.season, 2021)
        }
    }

    @Test
    fun `change season`() = runBlocking {
        viewModel.state.test {
            val latestItem = expectMostRecentItem()
            assertEquals(latestItem.rankingSeason.season, 2022)

            viewModel.onAction(ListAction.ChangeSeason(RankingSeason.SEASON_2023))
            assert(awaitItem().isLoading)
            val lastItem = expectMostRecentItem()
            assertEquals(lastItem.rankingSeason.season, 2023)
        }
    }
}