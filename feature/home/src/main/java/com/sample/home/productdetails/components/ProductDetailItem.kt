package com.sample.home.productdetails.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sample.domain.dto.login.products.DomainProduct
import com.sample.home.products.components.RatingBar

@Composable
fun ProductDetailItem(innerPadding: PaddingValues, product: DomainProduct) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(10.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp))
            .border(
                0.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        // Product Image
        AsyncImage(
            model = product.thumbnail,
            contentDescription = "${product.title} image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Title and Description
        Text(
            text = product.title.orEmpty(),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = product.description.orEmpty(),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Price and Discount
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "${product.discountPercentage}% off",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Stock and Rating
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = product.availabilityStatus.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                color = if (product.stock > 0) Color.DarkGray else Color.Red
            )
            RatingBar(
                rating = product.rating, modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Reviews Section with Flexible Height
        Text(
            text = "Reviews",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // Fill remaining space for reviews
        LazyColumn(
            modifier = Modifier
                .weight(1f) // Occupy the remaining space in the layout
                .padding(horizontal = 8.dp)
        ) {
            items(product.reviews.size) { index ->
                ReviewItem(review = product.reviews[index])
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Add to Cart Button (Always Visible)
        Button(
            onClick = { /* Add to Cart Action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text("Add to Cart")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewProductListItem() {
    val sampleDomainProduct = DomainProduct(
        availabilityStatus = "Low Stock",
        brand = "Essence",
        category = "beauty",
        description = "The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula.",
        discountPercentage = 7.17,
        id = 1,
        images = listOf(
            "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/1.png",
            "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/2.png"
        ),
        minimumOrderQuantity = 24,
        price = 9.99,
        rating = 4.94,
        returnPolicy = "30 days return policy",
        shippingInformation = "Ships in 1 month",
        sku = "RCH45Q1A",
        stock = 5,
        tags = listOf("beauty", "mascara"),
        thumbnail = "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/thumbnail.png",
        title = "Essence Mascara Lash Princess",
        warrantyInformation = "1 month warranty",
        weight = 2,
        emptyList()
    )
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Product Detail") },
            navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                    )
                }
            },
        )
    }) { innerPadding ->
        ProductDetailItem(innerPadding, product = sampleDomainProduct)
    }
}


