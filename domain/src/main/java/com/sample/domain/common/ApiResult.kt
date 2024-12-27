package com.sample.domain.common

sealed interface ApiResult<out T> {
    data class Success<out T>(val value: T) : ApiResult<T>

    data class GenericError(
        val code: Int? = null,
        val errorMessage: String? = null
    ) : ApiResult<Nothing>

    data object NetworkError : ApiResult<Nothing>
}
