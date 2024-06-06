package com.arda.auth.auth_api.usecase

import com.arda.core_api.domain.model.MinimizedUser
import com.arda.auth.auth_api.model.AuthTypeEnum
import com.arda.core_api.util.Resource

interface RegisterUseCase {
    suspend operator fun invoke(
        role : String,
        email: String = "",
        password: String = "",
        authType: AuthTypeEnum
    ): Resource<MinimizedUser>
}