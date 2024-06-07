package com.arda.case_api.domain.usecase

import com.arda.case_api.domain.model.Case

interface AddCaseUser {
    suspend operator fun invoke(case: Case): String
}