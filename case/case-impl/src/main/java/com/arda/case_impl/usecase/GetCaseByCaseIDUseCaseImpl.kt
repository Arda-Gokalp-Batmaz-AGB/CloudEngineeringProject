package com.arda.case_impl.usecase

import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.usecase.GetCaseByCaseIDUseCase
import com.arda.case_impl.data.repository.CaseDataRepository
import com.arda.core_api.util.Resource
import javax.inject.Inject

class GetCaseByCaseIDUseCaseImpl @Inject constructor(
    private val caseDataRepository: CaseDataRepository,
) : GetCaseByCaseIDUseCase {
    override suspend fun invoke(caseID: String): Resource<Case> {
        return caseDataRepository.getCaseByCaseID(caseID)
    }

}