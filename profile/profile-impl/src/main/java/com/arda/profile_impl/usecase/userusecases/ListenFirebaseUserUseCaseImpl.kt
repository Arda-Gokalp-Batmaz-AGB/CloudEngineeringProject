package com.arda.profile_impl.usecase.userusecases

import android.util.Log
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_api.util.Resource
import com.arda.profile_api.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

import javax.inject.Inject

//interfaceyap
class ListenFirebaseUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) {
    private val TAG = DebugTagsEnumUtils.DomainTag.tag
    suspend operator fun invoke(): Flow<Resource<MinimizedUser>> = channelFlow {
        Log.v(TAG, "TRIGGER THAT PLACE")
        // emit(Resource.Loading)
        userRepository.userStateChanges().collectLatest() { collectedUser ->
            if (collectedUser == null)
                send(Resource.Failure<MinimizedUser>(null))
            else
                send(Resource.Sucess(collectedUser))
        }
    }
}