package com.sample.data.remote.repo

import com.sample.data.remote.api.ProductsAPI
import com.sample.data.remote.mapper.ProductResponseMapper
import com.sample.domain.dto.login.products.ProductWrapper
import com.sample.domain.repo.ProductRepo
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepoImpl @Inject constructor(
    retrofit: Retrofit
) : ProductRepo {

    private val api = retrofit.create(ProductsAPI::class.java)

    override suspend fun getProducts(): ProductWrapper {
        return ProductResponseMapper().mapToDomainProductWrapper(api.getProducts())
    }
}