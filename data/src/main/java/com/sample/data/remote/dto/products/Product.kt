package com.sample.data.remote.dto.products

import com.sample.domain.dto.login.products.DomainProduct

data class Product(
    val availabilityStatus: String,
    val brand: String,
    val category: String,
    val description: String,
    val dimensions: Dimensions,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val meta: Meta,
    val minimumOrderQuantity: Int,
    val price: Double,
    val rating: Double,
    val returnPolicy: String,
    val reviews: List<Review>,
    val shippingInformation: String,
    val sku: String,
    val stock: Int,
    val tags: List<String>,
    val thumbnail: String,
    val title: String,
    val warrantyInformation: String,
    val weight: Int
) {
    fun mapToDomainProduct(): DomainProduct {
        return DomainProduct(
            availabilityStatus = availabilityStatus,
            brand = brand,
            category = category,
            description = description,
            discountPercentage = discountPercentage,
            id = id,
            images = images,
            minimumOrderQuantity = minimumOrderQuantity,
            price = price,
            rating = rating,
            returnPolicy = returnPolicy,
            shippingInformation = shippingInformation,
            sku = sku,
            stock = stock,
            tags = tags,
            thumbnail = thumbnail,
            title = title,
            warrantyInformation = warrantyInformation,
            weight = weight,
            reviews = reviews.map { it.mapToDomainReview() }
        )
    }
}