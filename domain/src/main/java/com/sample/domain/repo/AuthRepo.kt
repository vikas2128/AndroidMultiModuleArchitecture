package com.sample.domain.repo

import com.sample.domain.dto.login.response.LoginResponse

interface AuthRepo {
    suspend fun login(username:String,password:String): LoginResponse
}