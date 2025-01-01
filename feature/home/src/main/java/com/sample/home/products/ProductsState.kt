package com.sample.home.products

import com.sample.domain.common.UIMessage
import com.sample.domain.dto.login.products.DomainProduct

data class ProductsState(
    val products: List<DomainProduct> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: UIMessage? = null,
    val navigateToProductDetail: DomainProduct? = null
)