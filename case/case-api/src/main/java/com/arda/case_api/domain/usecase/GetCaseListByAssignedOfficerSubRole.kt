package com.arda.case_api.domain.usecase

import com.arda.case_api.domain.model.Case
import com.arda.core_api.domain.enums.OfficierSubRoleEnum
import com.arda.core_api.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetCaseListByAssignedOfficerSubRole {
    suspend operator fun invoke(assignedSubRole : OfficierSubRoleEnum): Resource<List<Case>>
}