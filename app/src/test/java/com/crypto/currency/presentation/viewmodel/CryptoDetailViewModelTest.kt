package com.crypto.currency.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.data.repository.CryptoDetailRepository
import com.crypto.currency.domain.usecase.GetCryptoDetailUseCase
import com.crypto.currency.domain.usecase.GetTopCryptosUseCase
import com.crypto.currency.utils.MockData
import com.crypto.currency.utils.Resource
import com.crypto.currency.utils.TestCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CryptoDetailViewModelTest {

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var repository: CryptoDetailRepository

    @Mock
    private lateinit var getCryptoDetailUseCase: GetCryptoDetailUseCase

    private lateinit var viewModel: CryptoDetailViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Override Main dispatcher

        viewModel = CryptoDetailViewModel(getCryptoDetailUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset Main dispatcher
    }

    @Test
    fun `fetchCryptoDetail should emit loading and then success`() = runTest {
        val mockCrypto = CryptoDomain(
            id = "bitcoin",
            name = "Bitcoin",
            symbol = "BTC",
            image = "https://example.com/bitcoin.png",
            currentPrice = 45000.0,
            marketCap = 850000000000,
            marketCapRank = 1,
            totalVolume = 2,
            high24h = 46000.0,
            low24h = 44000.0,
            priceChange24h = 25.0,
            priceChangePercentage24h = 21.0
        )

        `when`(getCryptoDetailUseCase.invoke("bitcoin")).thenReturn(flow { emit(Resource.Success(mockCrypto)) })

        viewModel.fetchCryptoDetail("bitcoin")

        viewModel.cryptoDetailState.test {
            assert(awaitItem() is Resource.Loading) //  First emission should be loading
            val emittedItem = awaitItem()
            kotlin.test.assertEquals(mockCrypto, (emittedItem as Resource.Success).data) //  Then success
        }
    }

    @Test
    fun `fetchCryptoDetail should emit loading and then error`() = runTest {
        `when`(getCryptoDetailUseCase.invoke("bitcoin")).thenReturn(flow { emit(Resource.Error("Network error")) })

        viewModel.fetchCryptoDetail("bitcoin")

        viewModel.cryptoDetailState.test {
            assert(awaitItem() is Resource.Loading) // First emission should be loading
            val error = awaitItem()
            assertTrue(error is Resource.Error)
            assertEquals("Network error", error.message) // Then error message
        }
    }

    @Test
    fun `fetchCryptoDetail should not emit new values if called with same id`() = runTest {
        val mockCrypto = CryptoDomain(
            id = "bitcoin",
            name = "Bitcoin",
            symbol = "BTC",
            image = "https://example.com/bitcoin.png",
            currentPrice = 45000.0,
            marketCap = 850000000000,
            marketCapRank = 1,
            totalVolume = 2,
            high24h = 46000.0,
            low24h = 44000.0,
            priceChange24h = 25.0,
            priceChangePercentage24h = 21.0
        )

        `when`(getCryptoDetailUseCase.invoke("bitcoin")).thenReturn(flow {
            emit(Resource.Success(mockCrypto))
        })

        viewModel.fetchCryptoDetail("bitcoin")
        advanceUntilIdle() // Ensure first request finishes

        viewModel.fetchCryptoDetail("bitcoin") // Calling same ID again

        verify(getCryptoDetailUseCase, times(1)).invoke("bitcoin") // Should be called only once
    }
}

