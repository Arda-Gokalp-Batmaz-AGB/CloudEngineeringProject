package com.arda.case_impl.data.repository

import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.model.Comment
import com.arda.core_api.domain.enums.OfficierSubRoleEnum
import com.arda.core_api.util.Resource

interface CaseDataRepository {
    suspend fun getAllCases(): Resource<List<Case>>
    suspend fun addCase(case: Case): String
    suspend fun addCaseComment(comment: Comment): Resource<Comment>
    suspend fun resolveCase(caseID: String): String
    suspend fun getCaseListByAssignedOfficerSubRole(assignedSubRole: String): Resource<List<Case>>
    suspend fun getCaseListByUserID(userID : String): Resource<List<Case>>
    suspend fun getCaseByCaseID(caseID: String): Resource<Case>
    suspend fun assignRoleToCase(caseID: String, role: String) : Resource<String>
    suspend fun removeCase(caseID: String) : String
}