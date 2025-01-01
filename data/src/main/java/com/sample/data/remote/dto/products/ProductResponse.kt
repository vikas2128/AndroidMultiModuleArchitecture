package com.sample.data.remote.dto.products

import com.sample.domain.dto.login.products.ProductWrapper

data class ProductResponse(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)