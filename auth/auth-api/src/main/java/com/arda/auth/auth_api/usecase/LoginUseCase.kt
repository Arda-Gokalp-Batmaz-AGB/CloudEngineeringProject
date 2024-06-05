package com.arda.auth.auth_api.usecase

import com.arda.core_api.domain.model.MinimizedUser
import com.arda.auth.auth_api.model.AuthTypeEnum
import com.arda.core_api.util.Resource

interface LoginUseCase {
    suspend operator fun invoke(
        email: String = "",
        password: String = "",
        verifyCode: String = "",
        authType: AuthTypeEnum
    ): Resource<MinimizedUser>
}