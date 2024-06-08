package com.arda.case_api.domain.usecase

import com.arda.case_api.domain.model.Case
import com.arda.core_api.util.Resource

interface GetAllCaseListUseCase {
    suspend operator fun invoke():Resource<List<Case>>
}