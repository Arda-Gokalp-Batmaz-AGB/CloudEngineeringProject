package com.arda.case_api.domain.usecase

import com.arda.core_api.util.Resource

interface RemoveCaseUseCase {
    suspend operator fun invoke(caseID : String): String
}