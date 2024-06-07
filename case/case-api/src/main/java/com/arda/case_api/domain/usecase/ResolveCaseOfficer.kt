package com.arda.case_api.domain.usecase

interface ResolveCaseOfficer {
    suspend operator fun invoke(caseID: String): String
}