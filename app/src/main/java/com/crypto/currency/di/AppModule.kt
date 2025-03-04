package com.crypto.currency.di

import android.content.Context
import androidx.room.Room
import com.crypto.currency.data.api.CryptoApi
import com.crypto.currency.data.db.CryptoDao
import com.crypto.currency.data.db.CryptoDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Ensures single instance across the app
object AppModule {

    private const val BASE_URL = "https://api.coingecko.com/api/v3/"

    // Provide HttpLoggingInterceptor
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Log full request and response
        }
    }

    // Provide OkHttpClient
    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Attach logging interceptor
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    // Provide Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()) // Use Moshi for JSON parsing
            .client(okHttpClient) // Attach OkHttpClient
            .build()
    }

    @Provides
    @Singleton
    fun provideCryptoApi(retrofit: Retrofit): CryptoApi {
        return retrofit.create(CryptoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CryptoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CryptoDatabase::class.java,
            "crypto_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCryptoDao(database: CryptoDatabase): CryptoDao {
        return database.cryptoDao()
    }
}
