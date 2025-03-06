package com.crypto.currency.presentation.viewmodel

import app.cash.turbine.test
import com.crypto.currency.domain.usecase.GetTopCryptosUseCase
import com.crypto.currency.utils.MockData
import com.crypto.currency.utils.Resource
import com.crypto.currency.utils.TestCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CryptoViewModelTest {

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var getTopCryptosUseCase: GetTopCryptosUseCase

    private lateinit var viewModel: CryptoViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Override Main dispatcher
        viewModel = CryptoViewModel(getTopCryptosUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset Main dispatcher
    }

    @Test
    fun `fetchTopCryptos should emit loading and then success`() = runTest {
        val mockData = MockData.cryptoList
        `when`(
            getTopCryptosUseCase.invoke(
                1,
                5
            )
        ).thenReturn(flow { emit(Resource.Success(mockData)) }) //  Use `flow {}` for proper emission

        viewModel.fetchTopCryptos() //  Start fetching BEFORE collecting state

        viewModel.cryptoState.test {
            assert(awaitItem() is Resource.Loading) //  First emission should be loading
            val emittedItem = awaitItem()
            assertEquals(mockData, (emittedItem as Resource.Success).data) //  Then success
        }

    }

    @Test
    fun `fetchTopCryptos should handle empty data`() = runTest {
        `when`(
            getTopCryptosUseCase.invoke(
                1,
                5
            )
        ).thenReturn(flow { emit(Resource.Success(emptyList())) }) //  Use `flow {}`

        viewModel.fetchTopCryptos()

        viewModel.cryptoState.test {
            assert(awaitItem() is Resource.Loading) //  Check loading first
            val emittedItem = awaitItem()
            assertEquals(emptyList(), (emittedItem as Resource.Success).data) //  Then empty success
        }
    }

    @Test
    fun `fetchTopCryptos should handle error state`() = runTest {
        `when`(
            getTopCryptosUseCase.invoke(
                1,
                5
            )
        ).thenReturn(flow { emit(Resource.Error("Network error")) }) //  Use `flow {}`

        viewModel.fetchTopCryptos()

        viewModel.cryptoState.test {
            assert(awaitItem() is Resource.Loading) //  Ensure loading is emitted
            val emittedItem = awaitItem()
            assert(emittedItem is Resource.Error) //  Ensure it's an error
            assertEquals(
                "Network error",
                (emittedItem as Resource.Error).message
            ) //  Validate error message
        }
    }

    @Test
    fun `loadNextPage should increase currentPage and fetch more data`() = runTest {

        val mockDataPage2 = listOf(MockData.cryptoDetail2)

        `when`(getTopCryptosUseCase.invoke(2, 5)).thenReturn(flow {
            emit(
                Resource.Success(
                    mockDataPage2
                )
            )
        }) //  Use `flow {}`

        viewModel.loadNextPage()

        viewModel.cryptoState.test {
            assert(awaitItem() is Resource.Loading) //  Check loading first

            val secondPage = awaitItem()
            assertEquals(mockDataPage2, (secondPage as Resource.Success).data) // Then first page

        }

    }
}
