package com.sample.home.products.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.sample.core.components.CircularProgressBar
import com.sample.domain.dto.login.products.DomainProduct
import com.sample.home.products.components.ProductList
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productsStateFlow: StateFlow<List<DomainProduct>>,
    loadingStateFlow: StateFlow<Boolean>,
    onProductClick: (DomainProduct) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product List") },
            )
        }
    ) { innerPadding ->
        val products = productsStateFlow.collectAsState()
        val loading = loadingStateFlow.collectAsState()
        CircularProgressBar(loading.value)
        ProductList(innerPadding, products = products.value, onItemClick = onProductClick)
    }
}