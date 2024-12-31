package com.sample.data.remote.repo

import com.sample.data.remote.api.ProductsAPI
import com.sample.data.remote.dto.products.Product
import com.sample.data.remote.dto.products.ProductResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class ProductRepoImplTest {
    @Mock
    lateinit var api: ProductsAPI

    lateinit var repo: ProductRepoImpl

    @Before
    fun setUp() {
        repo = ProductRepoImpl(api)
    }

    @Test
    fun `test getProduct to return DomainProduct`() = runBlocking {
        val productResponse = getProductResponse()
        whenever(api.getProducts()).thenReturn(productResponse)
        val resultList = repo.getProducts()
        Assert.assertEquals(productResponse.mapToDomainProduct(), resultList)
    }

    private fun getProductResponse(): ProductResponse {
        return ProductResponse(0, listOf(Product.dummyObj()), 0, 0)
    }
}