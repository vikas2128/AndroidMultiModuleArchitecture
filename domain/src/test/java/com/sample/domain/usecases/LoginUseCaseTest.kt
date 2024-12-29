package com.sample.domain.usecases

import com.sample.domain.common.Resource
import com.sample.domain.dto.login.response.LoginResponse
import com.sample.domain.repo.AuthRepo
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class LoginUseCaseTest {

    @Mock
    lateinit var repo: AuthRepo

    lateinit var useCase: LoginUseCase

    @Before
    fun setUp() {
        useCase = LoginUseCase(repo)
    }

    @Test
    fun `test login success`() = runBlocking {
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
        whenever(repo.login(USERNAME, PASSWORD)).thenReturn(loginResponse)

        val resultList = useCase(USERNAME, PASSWORD).toList()

        assert(resultList[0] is Resource.Loading)
        assert(resultList[1] is Resource.Success)

        assertEquals(loginResponse, (resultList[1] as Resource.Success).data)
    }

    @Test
    fun `test login error if username is blank`() = runBlocking {
        val resultList = useCase(EMPTY_STRING, PASSWORD).toList()
        assert(resultList[0] is Resource.Loading)
        assert(resultList[1] is Resource.Error)
    }

    @Test
    fun `test login error if password is blank`() = runBlocking {
        val resultList = useCase(USERNAME, EMPTY_STRING).toList()
        assert(resultList[0] is Resource.Loading)
        assert(resultList[1] is Resource.Error)
    }

    companion object {
        const val EMPTY_STRING = ""
        const val USERNAME = "test"
        const val PASSWORD = "test"
        const val DUMMY_STRING = "dummy"
        const val DUMMY_INT = 1
    }
}