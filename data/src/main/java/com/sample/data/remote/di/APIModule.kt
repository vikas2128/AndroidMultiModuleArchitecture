package com.sample.multimodulesample.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sample.data.remote.api.ProductsAPI
import com.sample.data.remote.repo.ProductRepoImpl
import com.sample.domain.common.Configurations
import com.sample.domain.repo.ProductRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModule {

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    @Singleton
    fun providesOkhttpCache(context: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        cache: Cache, loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val client: OkHttpClient.Builder =
            OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)

        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(Configurations.BASE_URL).client(client)
            .addConverterFactory(gsonConverterFactory)
    }

    @Provides
    @Singleton
    fun providesAuthApi(retrofit: Retrofit.Builder): ProductsAPI {
        return retrofit.build().create(ProductsAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthRepo(api: ProductsAPI): ProductRepo {
        return ProductRepoImpl(api)
    }
}