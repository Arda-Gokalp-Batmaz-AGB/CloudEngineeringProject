package com.arda.case_impl.usecase

import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.usecase.GetAllCaseListUseCase
import com.arda.case_impl.data.repository.CaseDataRepository
import com.arda.core_api.util.Resource
import javax.inject.Inject

class GetAllCaseListUseCaseImpl @Inject constructor(
    private val caseDataRepository: CaseDataRepository,
) : GetAllCaseListUseCase {
    override suspend fun invoke(): Resource<List<Case>> {
        return caseDataRepository.getAllCases()
    }

}