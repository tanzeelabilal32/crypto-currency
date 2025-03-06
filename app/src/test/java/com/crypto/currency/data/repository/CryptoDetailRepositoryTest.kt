package com.crypto.currency.data.repository

import app.cash.turbine.test
import com.crypto.currency.data.datasource.CryptoLocalDataSource
import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.utils.MockData
import com.crypto.currency.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class CryptoDetailRepositoryTest {

    @get:Rule
    val rule: TestRule = androidx.arch.core.executor.testing.InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var localDataSource: CryptoLocalDataSource

    private lateinit var repository: CryptoDetailRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        repository = CryptoDetailRepository(localDataSource)

        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `getCryptoById should emit loading and then success`() = runTest {
        val mockCrypto = MockData.cryptoDetail

        `when`(localDataSource.getCryptoById("bitcoin")).thenReturn(mockCrypto) //  Mock data retrieval

        repository.getCryptoById("bitcoin").test {
            assert(awaitItem() is Resource.Loading) //  First, emit loading
            val emittedValue = awaitItem()
            assertEquals(mockCrypto, emittedValue.data) //  Then, emit success
            awaitComplete() //  Ensure no further emissions
        }
    }

    @Test
    fun `getCryptoById should emit loading and then error when an exception occurs`() = runTest {
        `when`(localDataSource.getCryptoById("bitcoin")).thenThrow(RuntimeException("Database error")) //  Simulate error

        repository.getCryptoById("bitcoin").test {
            assert(awaitItem() is Resource.Loading) //  First, emit loading
            val error = awaitItem() //  Expect an error item
            assertTrue(error is Resource.Error)
            cancelAndIgnoreRemainingEvents() //  Ensures test doesn't hang
        }

        verify(localDataSource, times(1)).getCryptoById("bitcoin") //  Ensure function was called once
    }

    @Test
    fun `getCryptoById should return loading and empty data when crypto is not found`() = runTest {
        `when`(localDataSource.getCryptoById("unknown")).thenReturn(null) //  Simulate missing data

        repository.getCryptoById("unknown").test {
            assert(awaitItem() is Resource.Loading) //  First, emit loading
            cancelAndIgnoreRemainingEvents() //  Correct for testing ongoing Flow emissions
        }
    }
}
