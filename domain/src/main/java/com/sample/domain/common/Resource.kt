package com.sample.domain.common

sealed interface Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>
    class Error(val uiMessage: UIMessage) : Resource<Nothing>
    data object Loading : Resource<Nothing>
}