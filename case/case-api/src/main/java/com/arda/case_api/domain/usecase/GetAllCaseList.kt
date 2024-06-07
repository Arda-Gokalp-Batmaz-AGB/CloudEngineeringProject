package com.arda.case_api.domain.usecase

import com.arda.case_api.domain.model.Case
import com.arda.core_api.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetAllCaseList {
    suspend operator fun invoke(): Flow<Flow<Resource<List<Case>>>>
}