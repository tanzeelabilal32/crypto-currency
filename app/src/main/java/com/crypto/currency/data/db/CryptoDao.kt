package com.crypto.currency.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CryptoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cryptos: List<CryptoEntity>)

    @Query("SELECT * FROM cryptos ORDER BY marketCap DESC")
    suspend fun getTopCryptos(): List<CryptoEntity>
}
