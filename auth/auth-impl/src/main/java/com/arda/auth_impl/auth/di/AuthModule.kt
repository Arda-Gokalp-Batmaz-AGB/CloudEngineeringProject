package com.arda.auth_impl.auth.di

import com.arda.auth.auth_api.usecase.LoginUseCase
import com.arda.auth.auth_api.usecase.LogoutUseCase
import com.arda.auth.auth_api.usecase.RegisterUseCase
import com.arda.auth_impl.auth.data.repository.AuthRepository
import com.arda.auth_impl.auth.data.repository.AuthRepositoryImpl
import com.arda.auth_impl.auth.domain.usecase.LoginUseCaseImpl
import com.arda.auth_impl.auth.domain.usecase.LogoutUseCaseImpl
import com.arda.auth_impl.auth.domain.usecase.RegisterUseCaseImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
//    @Provides
//    @Singleton
//    fun auth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun AuthRepository(
        auth: FirebaseAuth,
    ): AuthRepository = AuthRepositoryImpl(auth)

    @Provides
    @Singleton
    fun loginUseCase(
        authRepository: AuthRepository,
    ): LoginUseCase = LoginUseCaseImpl(authRepository)

    @Provides
    @Singleton
    fun registerUseCase(
        authRepository: AuthRepository,
    ): RegisterUseCase = RegisterUseCaseImpl(authRepository)

    @Provides
    @Singleton
    fun logoutUseCase(
        authRepository: AuthRepository,
    ): LogoutUseCase = LogoutUseCaseImpl(authRepository)


    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher