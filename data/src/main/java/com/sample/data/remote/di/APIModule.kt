package com.sample.data.remote.di

import com.sample.data.remote.repo.ProductRepoImpl
import com.sample.domain.repo.ProductRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class APIModule {
    @Binds
    abstract fun bindApiService(impl: ProductRepoImpl): ProductRepo
}