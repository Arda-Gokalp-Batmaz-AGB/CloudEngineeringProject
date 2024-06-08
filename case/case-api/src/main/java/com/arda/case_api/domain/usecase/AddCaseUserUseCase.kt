package com.arda.case_api.domain.usecase

import com.arda.case_api.domain.model.Case

interface AddCaseUserUseCase {
    suspend operator fun invoke(case: Case): String
}