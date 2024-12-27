package com.sample.data.remote.api

import com.sample.domain.dto.login.request.LoginRequest
import com.sample.domain.dto.login.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("auth/login")
    suspend fun login(
        @Body data: LoginRequest
    ): LoginResponse
}