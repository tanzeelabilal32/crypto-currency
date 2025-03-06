package com.crypto.currency.data.repository

import app.cash.turbine.test
import com.crypto.currency.data.datasource.CryptoLocalDataSource
import com.crypto.currency.data.datasource.CryptoRemoteDataSource
import com.crypto.currency.utils.MockData
import com.crypto.currency.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CryptoRepositoryTest {

    @get:Rule
    val rule: TestRule = androidx.arch.core.executor.testing.InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var remoteDataSource: CryptoRemoteDataSource

    @Mock
    private lateinit var localDataSource: CryptoLocalDataSource

    private lateinit var repository: CryptoRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = CryptoRepository(remoteDataSource, localDataSource)
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `getTopCryptos should return success when API call is successful`() = runTest {

        `when`(remoteDataSource.fetchCryptos(1, 5)).thenReturn(MockData.cryptoList)

        repository.getTopCryptos(1, 5).test {
            assertEquals(Resource.Loading::class, awaitItem()::class)
            val emittedItem = awaitItem()
            assertEquals(Resource.Success::class, emittedItem::class)
            cancelAndConsumeRemainingEvents()
        }

        verify(localDataSource, times(1)).saveCryptos(MockData.cryptoList)
    }

    @Test
    fun `getTopCryptos should return error when API returns empty`() = runTest {
        `when`(remoteDataSource.fetchCryptos(1, 5)).thenReturn(emptyList())

        repository.getTopCryptos(1, 5).test {
            assertEquals(Resource.Loading::class, awaitItem()::class)
            val emittedItem = awaitItem()
            assertEquals(Resource.Error::class, emittedItem::class)
            cancelAndConsumeRemainingEvents()
        }

        verify(localDataSource, never()).saveCryptos(anyList())
    }

    @Test
    fun `getTopCryptos should return error when exception occurs`() = runTest {
        `when`(remoteDataSource.fetchCryptos(1, 5)).thenThrow(RuntimeException("API failure"))

        repository.getTopCryptos(1, 5).test {
            assertEquals(Resource.Loading::class, awaitItem()::class)
            val emittedItem = awaitItem()
            assertEquals(Resource.Error::class, emittedItem::class)
            cancelAndConsumeRemainingEvents()
        }

        verify(localDataSource, never()).saveCryptos(anyList())
    }
}
