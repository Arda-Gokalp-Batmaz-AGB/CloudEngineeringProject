package com.arda.case_api.domain.usecase

import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.model.Comment
import com.arda.core_api.util.Resource

interface AddCommentCase {
    suspend operator fun invoke(comment: Comment): Resource<Comment>
}