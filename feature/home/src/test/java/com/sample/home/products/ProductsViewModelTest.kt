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
        val uiMessage = UIMessage(FAILURE_MESSAGE, UIMessageType.NONE)
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

    private companion object {
        const val FAILURE_MESSAGE = "Failed to load data"
        private fun getProductList(): List<DomainProduct> {
            return listOf(DomainProduct.dummyObj())
        }
    }
}