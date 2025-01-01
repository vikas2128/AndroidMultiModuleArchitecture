package com.sample.data.remote.mapper

import com.sample.data.remote.dto.products.Dimensions
import com.sample.data.remote.dto.products.Meta
import com.sample.data.remote.dto.products.Product
import com.sample.data.remote.dto.products.ProductResponse
import com.sample.data.remote.dto.products.Review
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class ProductResponseMapperTest {
    private lateinit var productResponseMapper: ProductResponseMapper

    @Before
    fun setUp() {
        productResponseMapper = ProductResponseMapper()
    }

    @Test
    fun `mapToDomainProduct should map ProductResponse to ProductWrapper correctly`() {
        val mockDimensions = getDimensionsObject()
        val mockMeta = getMetaObject()
        val mockProduct = getProductObject(mockDimensions, mockMeta)
        val mockResponse = getProductResponseObject(mockProduct)

        val result = productResponseMapper.mapToDomainProductWrapper(mockResponse)

        assertEquals(mockResponse.limit, result.limit)
        assertEquals(mockResponse.skip, result.skip)
        assertEquals(mockResponse.total, result.total)
        assertEquals(mockResponse.products.size, result.products.size)

        val domainProduct = result.products.first()
        val originalProduct = mockResponse.products.first()

        assertEquals(originalProduct.id, domainProduct.id)
        assertEquals(originalProduct.title, domainProduct.title)
        assertEquals(originalProduct.description, domainProduct.description)
        assertEquals(originalProduct.price, domainProduct.price, 0.0)
        assertEquals(originalProduct.discountPercentage, domainProduct.discountPercentage, 0.0)
        assertEquals(originalProduct.rating, domainProduct.rating, 0.0)
        assertEquals(originalProduct.stock, domainProduct.stock)
        assertEquals(originalProduct.brand, domainProduct.brand)
        assertEquals(originalProduct.category, domainProduct.category)
        assertEquals(originalProduct.thumbnail, domainProduct.thumbnail)
        assertEquals(originalProduct.images, domainProduct.images)
        assertEquals(originalProduct.availabilityStatus, domainProduct.availabilityStatus)
        assertEquals(originalProduct.tags, domainProduct.tags)
        assertEquals(originalProduct.shippingInformation, domainProduct.shippingInformation)
        assertEquals(originalProduct.returnPolicy, domainProduct.returnPolicy)
        assertEquals(originalProduct.sku, domainProduct.sku)
        assertEquals(originalProduct.minimumOrderQuantity, domainProduct.minimumOrderQuantity)
        assertEquals(originalProduct.warrantyInformation, domainProduct.warrantyInformation)
        assertEquals(originalProduct.weight, domainProduct.weight)


        val originalReview = originalProduct.reviews.first()
        val domainReview = domainProduct.reviews.first()

        assertEquals(originalReview.comment, domainReview.comment)
        assertEquals(originalReview.date, domainReview.date)
        assertEquals(originalReview.rating, domainReview.rating)
        assertEquals(originalReview.reviewerEmail, domainReview.reviewerEmail)
        assertEquals(originalReview.reviewerName, domainReview.reviewerName)
    }

    private fun getProductResponseObject(mockProduct: Product): ProductResponse {
        val mockResponse = ProductResponse(
            limit = 10,
            skip = 0,
            total = 1,
            products = listOf(mockProduct)
        )
        return mockResponse
    }


    @Test
    fun `mapToDomainReview should map Review to DomainReview correctly`() {
        val review = getReviewObject()
        val result = productResponseMapper.mapToDomainReview(review)
        assertEquals(review.comment, result.comment)
        assertEquals(review.date, result.date)
        assertEquals(review.rating, result.rating)
        assertEquals(review.reviewerEmail, result.reviewerEmail)
        assertEquals(review.reviewerName, result.reviewerName)
    }


    @Test
    fun `mapToDomainProduct should handle empty product list`() {
        val mockResponse = ProductResponse(
            limit = 10,
            skip = 0,
            total = 0,
            products = emptyList()
        )

        val result = productResponseMapper.mapToDomainProductWrapper(mockResponse)

        assertEquals(mockResponse.limit, result.limit)
        assertEquals(mockResponse.skip, result.skip)
        assertEquals(mockResponse.total, result.total)
        assertEquals(0, result.products.size)
    }


    private companion object {
        private fun getReviewObject(): Review {
            val review = Review(
                comment = "Amazing!",
                date = "2025-01-02",
                rating = 5,
                reviewerEmail = "john.doe@example.com",
                reviewerName = "John Doe"
            )
            return review
        }

        private fun getProductObject(
            mockDimensions: Dimensions,
            mockMeta: Meta
        ): Product {
            val mockProduct = Product(
                id = 1,
                title = "Test Product",
                description = "Test Description",
                price = 100.0,
                discountPercentage = 10.0,
                rating = 4.5,
                stock = 50,
                brand = "Test Brand",
                category = "Test Category",
                thumbnail = "Test",
                images = listOf("Test"),
                availabilityStatus = "In Stock",
                tags = listOf("New", "Popular"),
                shippingInformation = "Ships in 2 days",
                returnPolicy = "30 days return",
                sku = "SKU123",
                minimumOrderQuantity = 1,
                warrantyInformation = "1 year warranty",
                weight = 1,
                reviews = listOf(getReviewObject()),
                dimensions = mockDimensions,
                meta = mockMeta
            )
            return mockProduct
        }

        private fun getMetaObject(): Meta {
            val mockMeta = Meta(
                barcode = "Test",
                createdAt = "Test",
                qrCode = "Test",
                updatedAt = "Test",
            )
            return mockMeta
        }

        private fun getDimensionsObject(): Dimensions {
            val mockDimensions = Dimensions(
                depth = 2.3,
                height = 2.3,
                width = 2.3,
            )
            return mockDimensions
        }
    }
}