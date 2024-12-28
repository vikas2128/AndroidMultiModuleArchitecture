package com.sample.home.products.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sample.domain.dto.login.products.DomainProduct

@Composable
fun ProductList(
    innerPadding: PaddingValues,
    products: List<DomainProduct>,
    onItemClick: (DomainProduct) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        items(products.size) { index ->
            ProductListItem(product = products[index], onClick = onItemClick)
        }
    }
}
