package com.arda.profile_impl.usecase.userusecases

import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.domain.usecase.GetMinimizedUserUseCase
import com.arda.profile_api.domain.repository.UserRepository
import javax.inject.Inject

class GetMinimizedUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : GetMinimizedUserUseCase {
    override fun invoke(): MinimizedUser? {
        return userRepository.currentFirebaseUser
    }
}