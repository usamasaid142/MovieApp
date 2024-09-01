package com.example.movieapp.di

import com.example.movieapp.BuildConfig
import com.example.movieapp.common.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    private val logging: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    private fun getOkHttpClient(): OkHttpClient {

        val okHttpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {

            okHttpClientBuilder
                .addInterceptor(logging)

        }

        okHttpClientBuilder.addInterceptor { chain ->

            try {
                val originalRequest = chain.request()
                val requestBuilder =
                    originalRequest.newBuilder()
              //  requestBuilder.addHeader("api_key", "570bf80849bdeec5c9968385d4643d35")
                requestBuilder.addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NzBiZjgwODQ5YmRlZWM1Yzk5NjgzODVkNDY0M2QzNSIsIm5iZiI6MTcyNTA5NTQ1OS43MTQzMywic3ViIjoiNjZkMmJiZjdlNzgyYTU4ZTRjMGMzZjk3Iiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.WJ2ZCsqf5LaIn7Pq2deSU-01W5D5NqEB87I-QqZawVM")
                chain.proceed(requestBuilder.build())
            } catch (exception: SocketTimeoutException) {
                exception.printStackTrace()
                chain.proceed(chain.request())
            }

        }

        return okHttpClientBuilder.build()
    }


    @Provides
    @Singleton
    fun provideRetrofitInstance(
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
            .create(ApiService::class.java)
    }

}