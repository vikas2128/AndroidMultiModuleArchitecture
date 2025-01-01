package com.sample.home.products.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.sample.domain.dto.login.products.DomainProduct
import com.sample.home.R
import com.sample.home.common.CircularProgressBar
import com.sample.home.common.ErrorView
import com.sample.home.products.ProductsState
import com.sample.home.products.components.ProductList
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productsStateFlow: StateFlow<ProductsState>,
    onProductClick: (DomainProduct) -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.product_list_title)) },
            )
        }
    ) { innerPadding ->
        val state = productsStateFlow.collectAsState().value

        LaunchedEffect(state.navigateToProductDetail) {
            state.navigateToProductDetail?.let { product ->
                onProductClick(product)
            }
        }

        when {
            state.isLoading -> {
                CircularProgressBar(true)
            }

            state.errorMessage != null -> {
                ErrorView(
                    message = state.errorMessage.message,
                    onRetry = onRetry
                )
            }

            else -> {
                ProductList(innerPadding, products = state.products, onItemClick = onProductClick)
            }
        }
    }
}