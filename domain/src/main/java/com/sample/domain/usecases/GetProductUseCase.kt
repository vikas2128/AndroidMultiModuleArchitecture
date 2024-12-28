package com.sample.domain.usecases

import com.sample.domain.common.ApiResponseHandler
import com.sample.domain.common.Resource
import com.sample.domain.common.safeApiCall
import com.sample.domain.dto.login.products.DomainProduct
import com.sample.domain.dto.login.products.ProductWrapper
import com.sample.domain.repo.AuthRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repo: AuthRepo,
) {
    operator fun invoke(): Flow<Resource<List<DomainProduct>>> =
        flow {
            emit(Resource.Loading)

            val apiResult = safeApiCall(Dispatchers.IO) {
                repo.getProducts()
            }
            emit(
                object : ApiResponseHandler<List<DomainProduct>, ProductWrapper>(
                    response = apiResult
                ) {
                    override suspend fun handleSuccess(resultObj: ProductWrapper): Resource<List<DomainProduct>> {
                        return Resource.Success(resultObj.products)
                    }
                }.getResult()
            )
        }
}