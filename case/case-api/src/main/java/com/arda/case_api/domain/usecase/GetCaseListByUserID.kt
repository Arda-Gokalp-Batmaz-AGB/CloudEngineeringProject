package com.arda.case_api.domain.usecase

import com.arda.case_api.domain.model.Case
import com.arda.core_api.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetCaseListByUserID {
    suspend operator fun invoke(userID : String): Flow<Flow<Resource<List<Case>>>>
}