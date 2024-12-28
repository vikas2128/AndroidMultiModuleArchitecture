package com.sample.data.remote.dto.products

import com.sample.domain.dto.login.products.DomainReview

data class Review(
    val comment: String,
    val date: String,
    val rating: Int,
    val reviewerEmail: String,
    val reviewerName: String
) {
    fun mapToDomainReview():DomainReview {
       return DomainReview(
           comment = comment,
           date = date,
           rating = rating,
           reviewerEmail = reviewerEmail,
           reviewerName = reviewerName
       )
    }
}