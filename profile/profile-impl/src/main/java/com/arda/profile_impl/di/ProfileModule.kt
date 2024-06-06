package com.arda.profile_impl.di

import com.arda.core_api.domain.usecase.GetMinimizedUserUseCase
import com.arda.profile_api.domain.repository.UserRepository
import com.arda.profile_impl.data.repository.UserRepositoryImpl
import com.arda.profile_impl.usecase.userusecases.GetMinimizedUserUseCaseImpl
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
object ProfileModule {

    @Provides
    @Singleton
    fun auth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun UserRepository(
        auth: FirebaseAuth,
    ): UserRepository = UserRepositoryImpl(auth)

    @Provides
    @Singleton
    fun getMinimizedUserUseCase(
        userRepository: UserRepository,
    ): GetMinimizedUserUseCase = GetMinimizedUserUseCaseImpl(userRepository)

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher
