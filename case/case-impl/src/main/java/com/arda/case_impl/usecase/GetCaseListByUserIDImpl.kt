package com.arda.case_impl.usecase

import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.usecase.GetCaseListByUserID
import com.arda.case_impl.data.repository.CaseDataRepository
import com.arda.core_api.util.Resource
import javax.inject.Inject

class GetCaseListByUserIDImpl @Inject constructor(
    private val caseDataRepository: CaseDataRepository,
) : GetCaseListByUserID {
    override suspend fun invoke(userID: String): Resource<List<Case>> {
        return caseDataRepository.getCaseListByUserID(userID)
    }

}