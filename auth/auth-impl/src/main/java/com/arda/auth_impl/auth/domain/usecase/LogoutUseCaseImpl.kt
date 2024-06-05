package com.arda.auth_impl.auth.domain.usecase

import com.arda.auth.auth_api.usecase.LogoutUseCase
import com.arda.auth_impl.auth.data.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : LogoutUseCase {
    override operator fun invoke(
    ) {
        authRepository.logout()
    }
}