package com.example.altntakip.di

import com.example.altntakip.api.GoldPriceApi
import com.example.altntakip.api.StockMarketApi
import com.example.altntakip.repo.ApiRepository
import com.example.altntakip.repo.ApiRepositoryImpl
import com.example.altntakip.util.Util.GOLD_API_KEY
import com.example.altntakip.util.Util.GOLD_BASE_URL
import com.example.altntakip.util.Util.MARKET_BASE_URL
import com.example.altntakip.view.HomeFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

/*
    @Provides
    @Singleton
    fun provideGoldRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.example.com/") // API base URL'ini burada belirtin
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }
    @Provides
    @Singleton
    fun provideGoldApiService(retrofit: Retrofit): GoldApiService {
        return retrofit.create(GoldApiService::class.java)
    }

 */
    @Provides
    fun provideAlphaVantageApi(): StockMarketApi {
        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()

        return Retrofit.Builder()
            .baseUrl(MARKET_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(StockMarketApi::class.java)
    }



    @Provides
    fun provideGOldApi(): GoldPriceApi {
      val httpClient = OkHttpClient.Builder().apply {
          addInterceptor { chain ->
              val originalRequest = chain.request()
              val requestWithApiKey = originalRequest.newBuilder()
                  .header("x-access-token", GOLD_API_KEY)
                  .build()
              chain.proceed(requestWithApiKey)
          }
          addInterceptor(HttpLoggingInterceptor().apply {
              level = HttpLoggingInterceptor.Level.BODY
          })
      }.build()

      return Retrofit.Builder()
          .baseUrl(GOLD_BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .client(httpClient)
          .build()
          .create(GoldPriceApi::class.java)

    }

    @Provides
    fun provideGoldRepository(stockMarketApi: StockMarketApi,goldApi : GoldPriceApi): ApiRepository {
        return ApiRepositoryImpl(stockMarketApi,goldApi)
    }
}
