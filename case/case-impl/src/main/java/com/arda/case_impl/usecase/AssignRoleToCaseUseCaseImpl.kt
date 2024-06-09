package com.arda.case_impl.usecase

import com.arda.case_api.domain.usecase.AssignRoleToCaseUseCase
import com.arda.case_impl.data.repository.CaseDataRepository
import com.arda.core_api.util.Resource
import javax.inject.Inject

class AssignRoleToCaseUseCaseImpl @Inject constructor(
    private val caseDataRepository: CaseDataRepository,
) : AssignRoleToCaseUseCase {
    override suspend fun invoke(caseID: String, role: String): Resource<String> {
        return caseDataRepository.assignRoleToCase(caseID,role)
    }
}