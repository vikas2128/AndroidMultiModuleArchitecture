package com.sample.data.remote.mapper

import com.sample.data.remote.dto.products.Product
import com.sample.data.remote.dto.products.ProductResponse
import com.sample.data.remote.dto.products.Review
import com.sample.domain.dto.login.products.DomainProduct
import com.sample.domain.dto.login.products.DomainReview
import com.sample.domain.dto.login.products.ProductWrapper

class ProductResponseMapper {

    fun mapToDomainProductWrapper(response: ProductResponse): ProductWrapper {
        return ProductWrapper(
            limit = response.limit,
            products = response.products.map { mapToDomainProduct(it) },
            skip = response.skip,
            total = response.total
        )
    }


    fun mapToDomainProduct(product: Product): DomainProduct {
        return DomainProduct(
            availabilityStatus = product.availabilityStatus,
            brand = product.brand,
            category = product.category,
            description = product.description,
            discountPercentage = product.discountPercentage,
            id = product.id,
            images = product.images,
            minimumOrderQuantity = product.minimumOrderQuantity,
            price = product.price,
            rating = product.rating,
            returnPolicy = product.returnPolicy,
            shippingInformation = product.shippingInformation,
            sku = product.sku,
            stock = product.stock,
            tags = product.tags,
            thumbnail = product.thumbnail,
            title = product.title,
            warrantyInformation = product.warrantyInformation,
            weight = product.weight,
            reviews = product.reviews.map { mapToDomainReview(it) }
        )
    }


    fun mapToDomainReview(review: Review): DomainReview {
        return DomainReview(
            comment = review.comment,
            date = review.date,
            rating = review.rating,
            reviewerEmail = review.reviewerEmail,
            reviewerName = review.reviewerName
        )
    }
}