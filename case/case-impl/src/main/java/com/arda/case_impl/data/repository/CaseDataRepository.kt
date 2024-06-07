package com.arda.case_impl.data.repository

import com.arda.case_api.domain.model.Case
import com.arda.core_api.util.Resource
import kotlinx.coroutines.flow.Flow

interface CaseDataRepository {
    suspend fun getAllCases(): Flow<Flow<Resource<List<Case>>>>
    suspend fun getCaseListByAssignedOfficerSubRole(): Flow<Flow<Resource<List<Case>>>>
    suspend fun getCaseListByID(): Flow<Flow<Resource<List<Case>>>>

}