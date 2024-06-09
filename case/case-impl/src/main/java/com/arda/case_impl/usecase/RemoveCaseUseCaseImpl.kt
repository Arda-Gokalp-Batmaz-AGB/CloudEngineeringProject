package com.arda.case_impl.usecase

import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.usecase.GetCaseListByUserIDUseCase
import com.arda.case_api.domain.usecase.RemoveCaseUseCase
import com.arda.case_impl.data.repository.CaseDataRepository
import com.arda.core_api.util.Resource
import javax.inject.Inject

class RemoveCaseUseCaseImpl @Inject constructor(
    private val caseDataRepository: CaseDataRepository,
) : RemoveCaseUseCase {
    override suspend fun invoke(caseID: String): String {
        return caseDataRepository.removeCase(caseID)
    }

}