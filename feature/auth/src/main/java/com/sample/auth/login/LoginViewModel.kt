package com.sample.auth.login

import androidx.lifecycle.viewModelScope
import com.sample.domain.common.BaseViewModel
import com.sample.domain.common.Resource
import com.sample.domain.dto.login.response.LoginResponse
import com.sample.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _loginResponse: MutableStateFlow<LoginResponse?> = MutableStateFlow(null)

    val loginResponse: StateFlow<LoginResponse?>
        get() = _loginResponse


    fun login(username: String, password: String) {
        loginUseCase.invoke(username, password).onEach { result ->
            withContext(Dispatchers.Main) {
                _loading.value = true
                when (result) {
                    is Resource.Success -> {
                        _loading.value = false
                        _loginResponse.value = result.data
                    }

                    is Resource.Loading -> {
                        _loading.value = true
                    }

                    is Resource.Error -> {
                        _loading.value = false
                        _messageState.value = result.uiMessage
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clearState() {
        _loginResponse.value = null
    }
}