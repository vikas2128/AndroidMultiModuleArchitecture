package com.sample.domain.usecases

import com.sample.domain.common.Resource
import com.sample.domain.dto.login.products.DomainProduct
import com.sample.domain.dto.login.products.ProductWrapper
import com.sample.domain.repo.ProductRepo
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
    lateinit var repo: ProductRepo

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
        return listOf(DomainProduct.dummyObj())
    }
}