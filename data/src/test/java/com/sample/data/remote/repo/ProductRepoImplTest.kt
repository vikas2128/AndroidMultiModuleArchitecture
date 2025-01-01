package com.sample.data.remote.repo

import com.sample.data.remote.api.ProductsAPI
import com.sample.data.remote.dto.products.Product
import com.sample.data.remote.dto.products.ProductResponse
import com.sample.data.remote.mapper.ProductResponseMapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import retrofit2.Retrofit

@RunWith(MockitoJUnitRunner::class)
class ProductRepoImplTest {

    private fun getProductResponse(): ProductResponse {
        return ProductResponse(0, listOf(Product.dummyObj()), 0, 0)
    }

    @Mock
    lateinit var retrofit: Retrofit

    @Mock
    lateinit var mockProductsAPI: ProductsAPI
    private lateinit var productRepo: ProductRepoImpl

    @Before
    fun setUp() {
        whenever(retrofit.create(ProductsAPI::class.java)).thenReturn(mockProductsAPI)
        productRepo = ProductRepoImpl(retrofit)
    }

    @Test
    fun `getProducts should return mapped domain products`() = runBlocking {
        val productResponse = getProductResponse()
        whenever(mockProductsAPI.getProducts()).thenReturn(productResponse)
        val actualResult = productRepo.getProducts()
        verify(mockProductsAPI).getProducts()
        Assert.assertEquals(ProductResponseMapper().mapToDomainProductWrapper(productResponse), actualResult)
    }
}
