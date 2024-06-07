package com.arda.case_impl.usecase

import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.usecase.ResolveCaseOfficer
import com.arda.case_impl.data.repository.CaseDataRepository
import com.arda.core_api.util.Resource
import javax.inject.Inject

class ResolveCaseOfficerImpl @Inject constructor(
    private val caseDataRepository: CaseDataRepository,
) : ResolveCaseOfficer {
    override suspend fun invoke(caseID: String): String {
        return caseDataRepository.resolveCase(caseID)
    }


}