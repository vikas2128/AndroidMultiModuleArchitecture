package com.sample.home.products

import com.sample.domain.dto.login.products.DomainProduct

sealed class ProductsIntent {
    object Initialize : ProductsIntent()
    object GetProducts : ProductsIntent()
    data class ShowProductDetail(val product: DomainProduct) : ProductsIntent()
}