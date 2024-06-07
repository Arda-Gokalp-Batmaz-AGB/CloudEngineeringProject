package com.arda.case_impl.data.repository

import com.arda.case_api.domain.model.Case
import com.arda.core_api.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CaseDataRepositoryImpl @Inject constructor(

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CaseDataRepository {
    override suspend fun getAllCases(): Flow<Flow<Resource<List<Case>>>>  =
        withContext(
            dispatcher
        ) {
        TODO("Not yet implemented")
    }

    override suspend fun getCaseListByAssignedOfficerSubRole(): Flow<Flow<Resource<List<Case>>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCaseListByID(): Flow<Flow<Resource<List<Case>>>> {
        TODO("Not yet implemented")
    }
}