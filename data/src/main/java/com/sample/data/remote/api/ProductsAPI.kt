package com.sample.data.remote.api

import com.sample.data.remote.dto.products.ProductResponse
import retrofit2.http.GET

interface ProductsAPI {
    @GET("products")
    suspend fun getProducts(): ProductResponse
}