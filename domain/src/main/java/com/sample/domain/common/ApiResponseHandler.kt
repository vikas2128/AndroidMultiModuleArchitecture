package com.sample.domain.common

import com.sample.domain.common.Configurations.NETWORK_ERROR

abstract class ApiResponseHandler<State, Data>(
    private val response: ApiResult<Data>,
) {
    suspend fun getResult(): Resource<State> {
        return when (response) {
            is ApiResult.GenericError -> {
                Resource.Error(
                    UIMessage(" Error: ${response.errorMessage}", UIMessageType.SNACKBAR)
                )
            }

            is ApiResult.NetworkError -> {
                Resource.Error(
                    UIMessage(" Error: $NETWORK_ERROR", UIMessageType.SNACKBAR)
                )
            }

            is ApiResult.Success -> {
                handleSuccess(resultObj = response.value)
            }
        }
    }

    abstract suspend fun handleSuccess(resultObj: Data): Resource<State>
}
