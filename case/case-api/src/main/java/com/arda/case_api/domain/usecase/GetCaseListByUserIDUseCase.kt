package com.arda.case_api.domain.usecase

import com.arda.case_api.domain.model.Case
import com.arda.core_api.util.Resource

interface GetCaseListByUserIDUseCase {
    suspend operator fun invoke(userID : String): Resource<List<Case>>
}