package com.sample.domain.dto.login.products

data class ProductWrapper(
    val limit: Int,
    val products: List<DomainProduct>,
    val skip: Int,
    val total: Int
)