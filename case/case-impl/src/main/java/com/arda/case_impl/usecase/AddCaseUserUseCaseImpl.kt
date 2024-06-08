package com.arda.case_impl.usecase

import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.usecase.AddCaseUserUseCase
import com.arda.case_impl.data.repository.CaseDataRepository
import javax.inject.Inject

class AddCaseUserUseCaseImpl @Inject constructor(
    private val caseDataRepository: CaseDataRepository,
) : AddCaseUserUseCase  {
    override suspend fun invoke(case: Case): String {
        return caseDataRepository.addCase(case)
    }

}