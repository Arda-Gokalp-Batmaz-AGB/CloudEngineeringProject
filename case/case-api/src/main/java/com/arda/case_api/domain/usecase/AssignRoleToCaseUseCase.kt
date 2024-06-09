package com.arda.case_api.domain.usecase

import com.arda.case_api.domain.model.Comment
import com.arda.core_api.util.Resource

interface AssignRoleToCaseUseCase {
    suspend operator fun invoke(caseID : String, role : String): Resource<String>
}