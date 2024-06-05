package com.arda.auth_impl.auth.di

import com.arda.auth_impl.auth.data.repository.AuthRepositoryImpl
import com.arda.auth_impl.auth.data.repository.AuthRepository
import com.arda.auth_impl.auth.domain.usecase.LoginUseCaseImpl
import com.arda.auth_impl.auth.domain.usecase.LogoutUseCaseImpl
import com.arda.auth_impl.auth.domain.usecase.RegisterUseCaseImpl
import com.arda.auth.auth_api.usecase.LoginUseCase
import com.arda.auth.auth_api.usecase.LogoutUseCase
import com.arda.auth.auth_api.usecase.RegisterUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun AuthRepository(
        auth: FirebaseAuth,
    ): AuthRepository = AuthRepositoryImpl(auth)

    @Provides
    @Singleton
    fun loginUseCase(
        authRepository: AuthRepository
    ) : LoginUseCase = LoginUseCaseImpl(authRepository)

    @Provides
    @Singleton
    fun registerUseCase(
        authRepository: AuthRepository
    ) : RegisterUseCase = RegisterUseCaseImpl(authRepository)

    @Provides
    @Singleton
    fun logoutUseCase(
        authRepository: AuthRepository
    ) : LogoutUseCase = LogoutUseCaseImpl(authRepository)
}