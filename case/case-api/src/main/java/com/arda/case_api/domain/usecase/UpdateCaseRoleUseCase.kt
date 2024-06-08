package com.arda.case_api.domain.usecase

interface UpdateCaseRoleUseCase {
    suspend operator fun invoke(caseID: String,role : String): String
}