package com.sample.domain.repo

import com.sample.domain.dto.login.products.ProductWrapper

interface ProductRepo {
    suspend fun getProducts(): ProductWrapper
}