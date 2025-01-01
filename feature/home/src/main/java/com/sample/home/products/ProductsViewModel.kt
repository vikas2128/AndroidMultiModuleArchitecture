package com.sample.home.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.domain.common.Resource
import com.sample.domain.dto.login.products.DomainProduct
import com.sample.domain.usecases.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ProductsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {

    private val _intentFlow = MutableSharedFlow<ProductsIntent>(replay = 0)
    val intentFlow: SharedFlow<ProductsIntent> = _intentFlow

    private val _stateFlow: MutableStateFlow<ProductsState> = MutableStateFlow(ProductsState())
    val stateFlow: StateFlow<ProductsState> = _stateFlow

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            _intentFlow.collect { intent ->
                when (intent) {
                    is ProductsIntent.Initialize -> initialize()
                    is ProductsIntent.GetProducts -> getProducts()
                    is ProductsIntent.ShowProductDetail -> showProductDetail(intent.product)
                }
            }
        }
    }

    private fun initialize() {
        if (!_stateFlow.value.isLoading) {
            _stateFlow.value = _stateFlow.value.copy(isLoading = true)
            viewModelScope.launch {
                _intentFlow.emit(ProductsIntent.GetProducts)
            }
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            getProductUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _stateFlow.value = ProductsState(
                            products = result.data,
                            isLoading = false,
                            errorMessage = null
                        )
                    }

                    is Resource.Loading -> {
                        _stateFlow.value = _stateFlow.value.copy(isLoading = true)
                    }

                    is Resource.Error -> {
                        _stateFlow.value = ProductsState(
                            products = emptyList(),
                            isLoading = false,
                            errorMessage = result.uiMessage
                        )
                    }
                }
            }
        }
    }

    private fun showProductDetail(product: DomainProduct) {
        _stateFlow.value = _stateFlow.value.copy(navigateToProductDetail = product)
    }

    fun clearNavigationEvent() {
        _stateFlow.value = _stateFlow.value.copy(navigateToProductDetail = null)
    }

    fun sendIntent(intent: ProductsIntent) {
        viewModelScope.launch {
            _intentFlow.emit(intent)
        }
    }
}