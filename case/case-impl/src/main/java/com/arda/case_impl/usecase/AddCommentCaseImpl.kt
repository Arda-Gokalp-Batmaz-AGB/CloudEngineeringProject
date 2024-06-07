package com.arda.case_impl.usecase

import com.arda.case_api.domain.model.Comment
import com.arda.case_api.domain.usecase.CommentCase
import com.arda.case_impl.data.repository.CaseDataRepository
import javax.inject.Inject

class AddCommentCaseImpl @Inject constructor(
    private val caseDataRepository: CaseDataRepository,
) : CommentCase {
    override suspend fun invoke(comment: Comment): String {
        return caseDataRepository.addCaseComment(comment)
    }

}