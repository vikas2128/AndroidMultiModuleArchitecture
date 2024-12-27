package com.sample.domain.usecases

import com.sample.domain.common.ApiResponseHandler
import com.sample.domain.common.Resource
import com.sample.domain.common.UIMessage
import com.sample.domain.common.UIMessageType
import com.sample.domain.common.safeApiCall
import com.sample.domain.dto.login.response.LoginResponse
import com.sample.domain.repo.AuthRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: AuthRepo,
) {
    operator fun invoke(
        username: String,
        password: String
    ): Flow<Resource<LoginResponse>> =
        flow {
            emit(Resource.Loading)

            if (username.isBlank()) {
                return@flow emit(
                    Resource.Error(
                        UIMessage(
                            "Please enter username.",
                            UIMessageType.SNACKBAR
                        )
                    )
                )
            }

            if (password.isBlank()) {
                return@flow emit(
                    Resource.Error(
                        UIMessage(
                            "Please enter password.",
                            UIMessageType.SNACKBAR
                        )
                    )
                )
            }

            val apiResult = safeApiCall(Dispatchers.IO) {
                repo.login(username, password)
            }

            emit(
                object :
                    ApiResponseHandler<LoginResponse, LoginResponse>(
                        response = apiResult
                    ) {
                    override suspend fun handleSuccess(resultObj: LoginResponse): Resource<LoginResponse> {
                        return Resource.Success(
                            resultObj
                        )
                    }
                }.getResult()
            )
        }
}