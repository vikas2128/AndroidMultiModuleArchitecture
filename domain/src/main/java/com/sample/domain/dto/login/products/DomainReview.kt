package com.sample.domain.dto.login.products

import kotlinx.serialization.Serializable

@Serializable
data class DomainReview(
    val comment: String,
    val date: String,
    val rating: Int,
    val reviewerEmail: String,
    val reviewerName: String
)