package com.sample.data.remote.dto.products

import com.sample.domain.dto.login.products.ProductWrapper

data class ProductResponse(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
) {
    fun mapToDomainProduct(): ProductWrapper {
        return ProductWrapper(
            limit = limit,
            products = products.map { it.mapToDomainProduct() },
            skip = skip,
            total = total
        )
    }
}