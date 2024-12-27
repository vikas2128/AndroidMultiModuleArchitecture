package com.sample.data.remote.repo

import com.sample.data.remote.api.AuthAPI
import com.sample.domain.dto.login.request.LoginRequest
import com.sample.domain.dto.login.response.LoginResponse
import com.sample.domain.repo.AuthRepo

class AuthRepoImpl(private val api: AuthAPI) : AuthRepo {
    override suspend fun login(username: String, password: String): LoginResponse {
        return api.login(LoginRequest(username, password))
    }
}