package com.arda.profile_api.domain.repository

import com.arda.core_api.domain.model.MinimizedUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val currentFirebaseUser: MinimizedUser?
//    var currentUser: User?
//    suspend fun getDetailedUserInfo(userId: String): Resource<User>
////    suspend fun getNotifications(userId: String): Resource<ArrayList<Notification>>
//    suspend fun updateProfile(updatedUserProfile: User): Resource<User>
//    suspend fun createDetailedUser(user : User) : Resource<User>
     fun userStateChanges(): Flow<MinimizedUser?>
}