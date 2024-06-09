package com.arda.case_ui.screens.userhome

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arda.case_api.domain.usecase.AssignRoleToCaseUseCase
import com.arda.case_api.domain.usecase.GetAllCaseListUseCase
import com.arda.case_api.domain.usecase.GetCaseListByAssignedOfficerSubRoleUseCase
import com.arda.case_api.domain.usecase.GetCaseListByUserIDUseCase
import com.arda.case_api.domain.usecase.RemoveCaseUseCase
import com.arda.core_api.domain.enums.RoleEnum
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.domain.usecase.GetMinimizedUserUseCase
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_api.util.Resource
import com.arda.core_api.util.copyOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserHomeViewModel @Inject constructor(
    private val getMinimizedUserUseCase: GetMinimizedUserUseCase,
    private val getCaseListByUserIDUseCase: GetCaseListByUserIDUseCase,
    private val getAllCaseListUseCase: GetAllCaseListUseCase,
    private val getCaseListByAssignedOfficerSubRoleUseCase: GetCaseListByAssignedOfficerSubRoleUseCase,
    private val assignRoleToCaseUseCase: AssignRoleToCaseUseCase,
    private val removeCaseUseCase: RemoveCaseUseCase
) : ViewModel(), LifecycleObserver {
    private val currentUser: MinimizedUser?
        get() = getMinimizedUserUseCase()
    private val _uiState = MutableStateFlow(UserHomeUiState(currentUser = currentUser))
    val uiState: StateFlow<UserHomeUiState> = _uiState.asStateFlow()


    private val TAG = DebugTagsEnumUtils.UITag.tag

    init {
        if (currentUser!!.role == RoleEnum.user.role) {
            listUserCases()
        } else if (currentUser!!.role == RoleEnum.admin.role) {
            listAdminUseCases()
        } else {
            listOfficierUseCases()
        }
    }

    fun onEvent(event: UserHomeEvent) {
        when (event) {
            UserHomeEvent.listUserCases -> listUserCases()
            is UserHomeEvent.selectUserCase -> selectCase(event.caseID)
            is UserHomeEvent.selectRole -> selectRole(event.caseID,event.role)
            is UserHomeEvent.removeCase -> removeCase(event.caseID)
        }
    }

    fun removeCase(caseID: String) = viewModelScope.launch {
        removeCaseUseCase(caseID)
        _uiState.update {
            it.copy(caseList = it.caseList.filter { x-> x.id != caseID }.copyOf().toList())
        }
    }
    fun selectRole(caseID: String,role: String) = viewModelScope.launch {//todo
        assignRoleToCaseUseCase(caseID,role)
//        _uiState.update {
//            it.copy(selectedRole = role)
//        }
    }

    fun selectCase(caseID: String) {
        _uiState.update {
            it.copy(selectedCaseID = caseID)
        }
    }

    fun listUserCases() = viewModelScope.launch {
        _uiState.update {
            it.copy(isLoading = true)
        }
        getCaseListByUserIDUseCase(currentUser!!.uid).let {
            when (it) {
                is Resource.Failure<*> -> Log.v(TAG, "ERROR ON FETCHING CASES")
                Resource.Loading -> TODO()
                is Resource.Sucess -> {
                    val cases = it.result
                    _uiState.update {
                        it.copy(caseList = cases.copyOf().toList(), isLoading = false)
                    }
                }
            }
        }
    }

    fun listAdminUseCases() = viewModelScope.launch {
        _uiState.update {
            it.copy(isLoading = true)
        }
        getAllCaseListUseCase().let {
            when (it) {
                is Resource.Failure<*> -> Log.v(TAG, "ERROR ON FETCHING CASES")
                Resource.Loading -> TODO()
                is Resource.Sucess -> {
                    val cases = it.result
                    _uiState.update {
                        it.copy(caseList = cases.copyOf().toList(), isLoading = false)
                    }
                }
            }
        }
    }

    fun listOfficierUseCases() = viewModelScope.launch {
        _uiState.update {
            it.copy(isLoading = true)
        }
        getCaseListByAssignedOfficerSubRoleUseCase(currentUser!!.role).let {
            when (it) {
                is Resource.Failure<*> -> Log.v(TAG, "ERROR ON FETCHING CASES")
                Resource.Loading -> TODO()
                is Resource.Sucess -> {
                    val cases = it.result
                    _uiState.update {
                        it.copy(caseList = cases.copyOf().toList(), isLoading = false)
                    }
                }
            }
        }
    }
}