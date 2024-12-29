package com.sample.home.products

import com.sample.domain.common.Resource
import com.sample.domain.common.UIMessage
import com.sample.domain.common.UIMessageType
import com.sample.domain.dto.login.products.DomainProduct
import com.sample.domain.usecases.GetProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ProductsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var useCase: GetProductUseCase
    lateinit var viewModel: ProductsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = ProductsViewModel(useCase)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test successful product fetch`() = runTest {
        val mockProducts = getProductList()
        whenever(useCase.invoke()).thenReturn(flow {
            emit(Resource.Loading)
            emit(Resource.Success(mockProducts))
        })
        viewModel.getProducts()
        advanceUntilIdle()
        assertEquals(mockProducts, viewModel.productsStateFlow.value)
    }

    @Test
    fun `test failed product fetch`() = runTest {
        val uiMessage = UIMessage("Failed to load data", UIMessageType.NONE)
        whenever(useCase.invoke()).thenReturn(flow {
            emit(Resource.Loading)
            emit(Resource.Error(uiMessage))
        })
        viewModel.getProducts()
        advanceUntilIdle()
        assertEquals(uiMessage, viewModel.messageState.value)
    }


    @Test
    fun `test getProducts is called`() = runTest {
        val mockProducts = getProductList()
        val successResource = Resource.Success(mockProducts)
        val mockFlow = flowOf(successResource)
        whenever(useCase.invoke()).thenReturn(mockFlow)
        viewModel.initialize()
        advanceUntilIdle()
        assertEquals(mockProducts, viewModel.productsStateFlow.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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