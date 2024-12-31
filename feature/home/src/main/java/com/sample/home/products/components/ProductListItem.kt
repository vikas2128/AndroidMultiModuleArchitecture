package com.sample.home.products.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sample.domain.dto.login.products.DomainProduct
import com.sample.home.R

@Composable
fun ProductListItem(product: DomainProduct, onClick: (DomainProduct) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(product) }
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp))
            .border(
                0.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                RoundedCornerShape(8.dp)
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = product.thumbnail,
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground)
            ),
            contentDescription = "${product.title} thumbnail",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = product.title!!,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.availabilityStatus!!,
                style = MaterialTheme.typography.bodySmall,
                color = if (product.stock > 0) Color.DarkGray else Color.Red
            )
        }

        RatingBar(
            rating = product.rating,
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(10.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewProductListItem() {
    ProductListItem(product = DomainProduct.dummyObj(), onClick = {})
}