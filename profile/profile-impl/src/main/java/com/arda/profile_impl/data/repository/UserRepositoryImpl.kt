package com.arda.profile_impl.data.repository

import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.profile_api.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserRepository {
    override val currentFirebaseUser: MinimizedUser?
        get() = auth.currentUser.let { x -> x?.let { MinimizedUser(it.uid,it.displayName!!.capitalize()!!,it.email!!) } }
    private val TAG = DebugTagsEnumUtils.DataTag.tag
    override fun userStateChanges(): Flow<MinimizedUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            //trySend(auth.currentUser.let { x-> x?.let { MinimizedUser(it.uid) } }).isSuccess
            trySend(auth.currentUser.let { x -> x?.let { MinimizedUser(it.uid,it.displayName!!.capitalize()!!,it.email!!) } }).isSuccess
        }

        // Add the listener to FirebaseAuth
        auth.addAuthStateListener(listener)

        // When the Flow is closed or cancelled, remove the auth state listener
        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }


}