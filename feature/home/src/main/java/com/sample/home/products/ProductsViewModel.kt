package com.sample.home.products

import androidx.lifecycle.viewModelScope
import com.sample.home.common.BaseViewModel
import com.sample.domain.common.Resource
import com.sample.domain.dto.login.products.DomainProduct
import com.sample.domain.usecases.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
open class ProductsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
) : BaseViewModel() {

    private val _productsStateFlow: MutableStateFlow<List<DomainProduct>> = MutableStateFlow(
        emptyList()
    )

    val productsStateFlow: StateFlow<List<DomainProduct>>
        get() = _productsStateFlow


    private var isInitialized = false

    fun initialize() {
        if (!isInitialized) {
            isInitialized = true
            getProducts()
        }
    }

    fun getProducts() {
        getProductUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _productsStateFlow.value = result.data
                    _loading.value = false
                }

                is Resource.Loading -> {
                    _loading.value = true
                }

                is Resource.Error -> {
                    _loading.value = false
                    _messageState.value = result.uiMessage
                }
            }
        }.launchIn(viewModelScope)
    }
}