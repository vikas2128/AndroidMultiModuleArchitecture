package com.sample.data.remote.dto.products

import com.sample.domain.dto.login.products.DomainProduct

data class Product(
    val availabilityStatus: String,
    val brand: String,
    val category: String,
    val description: String,
    val dimensions: Dimensions?,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val meta: Meta?,
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


    companion object {
        fun dummyObj(): Product {
            return Product(
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
                reviews = emptyList(),
                meta = null,
                dimensions = null
            )
        }
    }
}