package com.arda.case_impl.usecase

import com.arda.case_api.domain.usecase.ResolveCaseOfficerUseCase
import com.arda.case_impl.data.repository.CaseDataRepository
import javax.inject.Inject

class ResolveCaseOfficerUseCaseImpl @Inject constructor(
    private val caseDataRepository: CaseDataRepository,
) : ResolveCaseOfficerUseCase {
    override suspend fun invoke(caseID: String): String {
        return caseDataRepository.resolveCase(caseID)
    }


}