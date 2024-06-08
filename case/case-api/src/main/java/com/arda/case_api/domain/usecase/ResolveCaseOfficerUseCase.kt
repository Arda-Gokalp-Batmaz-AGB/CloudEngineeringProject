package com.arda.case_api.domain.usecase

interface ResolveCaseOfficerUseCase {
    suspend operator fun invoke(caseID: String): String
}