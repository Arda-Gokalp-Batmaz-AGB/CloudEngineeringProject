package com.arda.case_impl.usecase

import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.usecase.GetCaseListByAssignedOfficerSubRoleUseCase
import com.arda.case_impl.data.repository.CaseDataRepository
import com.arda.core_api.domain.enums.OfficierSubRoleEnum
import com.arda.core_api.util.Resource
import javax.inject.Inject

class GetCaseListByAssignedOfficerSubRoleUseCaseImpl @Inject constructor(
    private val caseDataRepository: CaseDataRepository,
) : GetCaseListByAssignedOfficerSubRoleUseCase {
    override suspend fun invoke(assignedSubRole: OfficierSubRoleEnum): Resource<List<Case>> {
       return caseDataRepository.getCaseListByAssignedOfficerSubRole(assignedSubRole)
    }
}