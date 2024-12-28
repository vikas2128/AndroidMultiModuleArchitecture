package com.sample.home.products

import androidx.lifecycle.viewModelScope
import com.sample.domain.common.BaseViewModel
import com.sample.domain.common.Resource
import com.sample.domain.dto.login.products.DomainProduct
import com.sample.domain.usecases.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
) : BaseViewModel() {

    private val _productsStateFlow: MutableStateFlow<List<DomainProduct>> = MutableStateFlow(
        emptyList()
    )

    val productsStateFlow: StateFlow<List<DomainProduct>>
        get() = _productsStateFlow


    init {
        getProducts()
    }

    fun getProducts() {
        getProductUseCase().onEach { result ->
            withContext(Dispatchers.Main) {
                _loading.value = true
                when (result) {
                    is Resource.Success -> {
                        _loading.value = false
                        _productsStateFlow.value = result.data
                    }

                    is Resource.Loading -> {
                        _loading.value = true
                    }

                    is Resource.Error -> {
                        _loading.value = false
                        messageHandler(result.uiMessage)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clearState() {
        _productsStateFlow.value = emptyList()
    }
}