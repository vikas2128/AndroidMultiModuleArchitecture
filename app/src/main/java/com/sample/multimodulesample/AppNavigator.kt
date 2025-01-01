package com.sample.multimodulesample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sample.domain.dto.login.products.DomainProduct
import com.sample.home.CustomNavTypes
import com.sample.home.productdetails.screen.ProductDetailScreen
import com.sample.home.products.ProductsIntent
import com.sample.home.products.ProductsViewModel
import com.sample.home.products.screen.ProductListScreen
import com.sample.multimodulesample.NavigationItem.Product
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Composable
fun CreateAppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Product) {
        composable<Product> { backStackEntry ->
            val productsViewModel: ProductsViewModel = hiltViewModel()

            LaunchedEffect(Unit) {
                productsViewModel.sendIntent(ProductsIntent.Initialize)
            }

            ProductListScreen(
                productsViewModel.stateFlow,
                onProductClick = {
                    navController.navigate(NavigationItem.ProductDetail(it))
                    productsViewModel.clearNavigationEvent()
                },
                onRetry={
                    productsViewModel.sendIntent(ProductsIntent.GetProducts)
                })

        }

        composable<NavigationItem.ProductDetail>(
            typeMap = mapOf(typeOf<DomainProduct>() to CustomNavTypes.DomainProductType)
        ) { backStackEntry ->
            val product: NavigationItem.ProductDetail = backStackEntry.toRoute()
            ProductDetailScreen(product.domainProduct, onBackClick = {
                navController.popBackStack()
            })
        }
    }
}

sealed class NavigationItem {

    @Serializable
    data object Product : NavigationItem()

    @Serializable
    data class ProductDetail(val domainProduct: DomainProduct) : NavigationItem()

}

