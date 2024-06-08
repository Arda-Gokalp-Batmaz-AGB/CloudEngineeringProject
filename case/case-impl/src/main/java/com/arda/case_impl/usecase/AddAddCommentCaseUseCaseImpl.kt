package com.arda.case_impl.usecase

import com.arda.case_api.domain.model.Comment
import com.arda.case_api.domain.usecase.AddCommentCaseUseCase
import com.arda.case_impl.data.repository.CaseDataRepository
import com.arda.core_api.util.Resource
import javax.inject.Inject

class AddAddCommentCaseUseCaseImpl @Inject constructor(
    private val caseDataRepository: CaseDataRepository,
) : AddCommentCaseUseCase {
    override suspend fun invoke(comment: Comment): Resource<Comment> {
        return caseDataRepository.addCaseComment(comment)
    }

}