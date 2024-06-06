package com.arda.auth_impl.auth.domain.usecase

import com.arda.auth.auth_api.model.AuthTypeEnum
import com.arda.auth.auth_api.usecase.RegisterUseCase
import com.arda.auth_impl.auth.data.repository.AuthRepository
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.util.Resource
import com.arda.core_api.validation.ValidationUtil
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : RegisterUseCase {
    override suspend operator fun invoke(
        role: String,
        email: String,
        password: String,
        authType: AuthTypeEnum,
    ): Resource<MinimizedUser> {
        var result: Resource<MinimizedUser>
        if (authType == AuthTypeEnum.REGISTER_WITH_EMAIL) {
            result = emailRegister(email = email, role = role, password = password)
        } else {
            result =
                Resource.Failure<MinimizedUser>(
                    exceptionList = listOf(Exception("Error empty areas"))
                )
        }
        return result
    }


    private suspend fun emailRegister(
        email: String,
        role: String,
        password: String,
    ): Resource<MinimizedUser> {
        //EMAİL VALIDATE
        val validationErrorsList = arrayListOf<Exception>()

        var passwordValidationResult = ValidationUtil.validatePassword(password)
        var emailValidationResult = ValidationUtil.validateEmail(email)
        if (!passwordValidationResult.isValid)
            validationErrorsList.add(passwordValidationResult.exception)
        if (!emailValidationResult.isValid)
            validationErrorsList.add(emailValidationResult.exception)

        if (validationErrorsList.size == 0) {
            return authRepository.emailRegister(email = email, role = role, password = password)
        }
        return Resource.Failure<FirebaseUser>(
            exceptionList = validationErrorsList
        )
    }
}