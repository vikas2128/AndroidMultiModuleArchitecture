package com.sample.home.productdetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sample.domain.dto.login.products.DomainReview

@Composable
fun ReviewItem(review: DomainReview) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "${review.reviewerName} - ${review.rating}★",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = review.comment,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Divider(modifier = Modifier.padding(vertical = 4.dp))
    }
}