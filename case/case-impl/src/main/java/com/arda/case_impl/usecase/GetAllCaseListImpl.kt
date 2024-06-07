package com.arda.case_impl.usecase

import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.usecase.GetAllCaseList
import com.arda.case_impl.data.repository.CaseDataRepository
import com.arda.core_api.util.Resource
import javax.inject.Inject

class GetAllCaseListImpl @Inject constructor(
    private val caseDataRepository: CaseDataRepository,
) : GetAllCaseList {
    override suspend fun invoke(): Resource<List<Case>> {
        return caseDataRepository.getAllCases()
    }

}