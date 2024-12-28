package com.sample.data.remote.api

import com.sample.data.remote.dto.products.ProductResponse
import com.sample.domain.dto.login.request.LoginRequest
import com.sample.domain.dto.login.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthAPI {
    @POST("auth/login")
    suspend fun login(
        @Body data: LoginRequest
    ): LoginResponse

    @GET("products")
    suspend fun getProducts(): ProductResponse
}