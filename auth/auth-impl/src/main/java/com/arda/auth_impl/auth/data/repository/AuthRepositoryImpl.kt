package com.arda.auth_impl.auth.data.repository

import android.util.Log
import com.arda.auth_impl.auth.util.await
import com.arda.core_api.domain.enums.OfficierSubRoleEnum
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_api.util.Resource
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : AuthRepository {
    private var tempVerifyID: String? = null
    override val currentUser: MinimizedUser?
        get() = auth.currentUser.let { x -> x?.uid?.let { MinimizedUser(it,x.displayName!!) } }

    private var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(DebugTagsEnumUtils.UITag.tag, "onVerificationCompleted:$credential")
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w(DebugTagsEnumUtils.UITag.tag, "onVerificationFailed", e)
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            tempVerifyID = verificationId
            Log.d(DebugTagsEnumUtils.UITag.tag, "onCodeSent:$verificationId")
        }
    }

    override suspend fun emailLogin(email: String, password: String): Resource<MinimizedUser> =
        withContext(
            dispatcher
        ) {
            return@withContext try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                Resource.Sucess(result.user!!.let { x -> MinimizedUser(x.uid,x.displayName!!) })
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Failure<Exception>(e)
            }
        }

    override suspend fun emailRegister(email: String, role: String,password: String): Resource<MinimizedUser> =
        withContext(
            dispatcher
        ) {
            return@withContext try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                result?.user?.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName(role).build()
                )?.await()

                Resource.Sucess(result.user!!.let { x -> MinimizedUser(x.uid,x.displayName!!) })
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Failure<Exception>(e)
            }
        }

    override suspend fun phoneLogin(verifyCode: String): Resource<MinimizedUser> = withContext(
        dispatcher
    ) {
        return@withContext try {
            val credential = PhoneAuthProvider.getCredential(tempVerifyID!!, verifyCode)
            val result = auth.signInWithCredential(credential).await()
            Resource.Sucess(result.user!!.let { x -> MinimizedUser(x.uid,x.displayName!!) })
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure<Exception>(e)
        }
    }

    override fun getVerifyCode(phone: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callbacks)
            //.setActivity(null)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun logout() {
        auth.signOut()
    }
}