package com.arda.core_api.domain.usecase

import com.arda.core_api.domain.model.MinimizedUser
interface GetMinimizedUserUseCase {
    operator fun invoke() : MinimizedUser?
}