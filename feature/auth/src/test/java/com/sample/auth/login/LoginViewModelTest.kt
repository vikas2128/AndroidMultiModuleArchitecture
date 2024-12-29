package com.sample.auth.login

import com.sample.domain.common.Resource
import com.sample.domain.common.UIMessage
import com.sample.domain.common.UIMessageType
import com.sample.domain.dto.login.response.LoginResponse
import com.sample.domain.usecases.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var useCase: LoginUseCase
    lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = LoginViewModel(useCase)
        Dispatchers.setMain(testDispatcher)
    }


    @Test
    fun `test login method for loading and success`() = runTest {
        val loginResponse = LoginResponse(
            DUMMY_STRING,
            DUMMY_STRING,
            DUMMY_STRING,
            DUMMY_STRING,
            DUMMY_INT,
            DUMMY_STRING,
            DUMMY_STRING,
            DUMMY_STRING,
            DUMMY_STRING,
        )

        whenever(useCase.invoke(USERNAME, PASSWORD)).thenReturn(flow {
            emit(Resource.Loading)
            emit(Resource.Success(loginResponse))
        })

        viewModel.login(USERNAME, PASSWORD)
        advanceUntilIdle()
        assertEquals(loginResponse, viewModel.loginResponse.value)
    }

    @Test
    fun `test login use case for loading success and error`() = runTest {
        val uiMessage = UIMessage("Failed to load data", UIMessageType.NONE)
        whenever(useCase.invoke(USERNAME, PASSWORD)).thenReturn(flow {
            emit(Resource.Loading)
            emit(Resource.Error(uiMessage))
        })
        viewModel.login(USERNAME, PASSWORD)
        advanceUntilIdle()
        assertEquals(uiMessage, viewModel.messageState.value)
    }

    @Test
    fun `test clearState for _loginResponse is set to null `() = runTest {
        viewModel.clearState()
        advanceUntilIdle()
        assertEquals(null, viewModel.loginResponse.value)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    companion object {
        const val USERNAME = "test"
        const val PASSWORD = "test"
        const val DUMMY_STRING = "dummy"
        const val DUMMY_INT = 1
    }
}