package com.arda.auth_impl.auth.data.repository

import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.util.Resource

interface AuthRepository {
    val currentUser: MinimizedUser?
    suspend fun emailLogin(email: String, password: String): Resource<MinimizedUser>
    suspend fun emailRegister(email: String, password: String): Resource<MinimizedUser>
    suspend fun phoneLogin(verifyCode: String): Resource<MinimizedUser>
    fun getVerifyCode(phone: String)
    fun logout()
}