package com.sample.domain.usecases

import com.sample.domain.common.Resource
import com.sample.domain.dto.login.products.DomainProduct
import com.sample.domain.dto.login.products.ProductWrapper
import com.sample.domain.repo.AuthRepo
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetProductUseCaseTest {

    @Mock
    lateinit var repo: AuthRepo

    lateinit var useCase: GetProductUseCase

    @Before
    fun setUp() {
        useCase = GetProductUseCase(repo)
    }

    @Test
    fun `test getProduct Loading success`() = runBlocking {
        val list = getProductList()
        val productResponse = ProductWrapper(0, list, 0, 0)
        whenever(repo.getProducts()).thenReturn(productResponse)

        val resultList = useCase().toList()

        assert(resultList[0] is Resource.Loading)
        assert(resultList[1] is Resource.Success)

        Assert.assertEquals(list, (resultList[1] as Resource.Success).data)
    }


    private fun getProductList(): List<DomainProduct> {
        return listOf(
            DomainProduct(
                availabilityStatus = "Low Stock",
                brand = "Essence",
                category = "beauty",
                description = "The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula.",
                discountPercentage = 7.17,
                id = 1,
                images = listOf(
                    "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/1.png",
                    "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/2.png"
                ),
                minimumOrderQuantity = 24,
                price = 9.99,
                rating = 4.94,
                returnPolicy = "30 days return policy",
                shippingInformation = "Ships in 1 month",
                sku = "RCH45Q1A",
                stock = 5,
                tags = listOf("beauty", "mascara"),
                thumbnail = "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/thumbnail.png",
                title = "Essence Mascara Lash Princess",
                warrantyInformation = "1 month warranty",
                weight = 2,
                emptyList()
            )
        )
    }
}