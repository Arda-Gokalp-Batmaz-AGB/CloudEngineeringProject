package com.arda.case_api.domain.usecase

import com.arda.case_api.domain.model.Case
import com.arda.core_api.util.Resource

interface GetCaseByCaseID { //    suspend operator fun invoke() : Resource<Case>
    suspend operator fun invoke(caseID: String): Resource<Case>
}
