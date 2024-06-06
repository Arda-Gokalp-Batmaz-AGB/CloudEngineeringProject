package com.arda.cloudengineeringproject.core.waitscreen

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.arda.core_api.domain.usecase.GetMinimizedUserUseCase
import com.arda.core_api.util.DebugTagsEnumUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WaitScreenViewModel @Inject constructor(
    private val getMinimizedUserUseCase: GetMinimizedUserUseCase,
) : ViewModel(), LifecycleObserver {
    private val TAG = DebugTagsEnumUtils.UITag.tag
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    private val _uiState = MutableStateFlow(WaitScreenUiState())
    val uiState: StateFlow<WaitScreenUiState> = _uiState.asStateFlow()

    init {
        Log.v(TAG,"WAIT SCREEN INITS")
        callInit()
    }
    fun callInit() {
        val minimizedUser = getMinimizedUserUseCase()
        if(minimizedUser == null){
            _uiState.update {
                it.copy(navigatedScreen = PossibleNavigations.AUTH_SCREEN)
            }
        }
        else{
            _uiState.update {
                it.copy(navigatedScreen = PossibleNavigations.HOME_SCREEN)
            }
//            getDetailedUserProfile()
        }

        Log.v(TAG, "LoggedCheckViewModel init works minuser ${minimizedUser}")
    }
//
//    private fun getDetailedUserProfile() = viewModelScope.launch {
//        getSessionUseCaseImpl().collectLatest {
//            lateinit var user: Resource<User>
//            it.onEach { sessionFLow ->
//                if (sessionFLow == null) {
//                    Log.v(TAG, "NEVER TRIGGER")
//                    user = Resource.Failure<User>(null)
//                    _uiState.update {
//                        it.copy(navigatedScreen = PossibleNavigations.SURVEY_SCREEN)
//                    }
//                } else {
//                    user = Resource.Sucess(sessionFLow.user)
//                    _uiState.update {
//                        it.copy(navigatedScreen = PossibleNavigations.HOME_SCREEN)
//                    }
////                    if (syncToFireStore == false) {
////                        syncUserToFireStoreUseCaseImpl(currentDetailedUser!!)
////                        syncToFireStore = true
////                        Log.v(TAG, "SYNCED TO FIRESTORE SUCESS")
////                    }
//                }
//                _uiState.update {
//                    it.copy(detailedUserFlow = user)
//                }
//
//                Log.v(
//                    TAG,
//                    "In LoggedCheckViewModel current detailed user: $sessionFLow"
//                )
//            }.launchIn(viewModelScope)
//        }
//
//    }
}