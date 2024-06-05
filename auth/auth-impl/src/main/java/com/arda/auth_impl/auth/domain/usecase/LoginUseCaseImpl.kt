package com.arda.auth_impl.auth.domain.usecase

import com.arda.auth_impl.auth.data.repository.AuthRepository
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.auth.auth_api.model.AuthTypeEnum
import com.arda.auth.auth_api.usecase.LoginUseCase
import com.arda.core_api.util.Resource
import com.arda.core_api.validation.ValidationUtil
import java.lang.Exception
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : LoginUseCase {
    override suspend operator fun invoke(
        email: String,
        password: String,
        verifyCode: String ,
        authType: AuthTypeEnum
    ): Resource<MinimizedUser> {

        var result: Resource<MinimizedUser>
        if (authType == AuthTypeEnum.LOGIN_WITH_EMAIL) {
            result = emailLogin(email = email, password = password)
        } else {
            result =
                Resource.Failure<MinimizedUser>(
                    exceptionList = listOf(Exception("Error empty areas"))
                )
        }
        return result
    }

    private suspend fun emailLogin(email: String, password: String): Resource<MinimizedUser> {
        //EMAİL VALIDATE
        var emailValidationResult = ValidationUtil.validateEmail(email)
        if (emailValidationResult.isValid) {
            return authRepository.emailLogin(email, password)
        }
        return Resource.Failure<MinimizedUser>(
            exceptionList = listOf(emailValidationResult.exception)
        )
    }
}
