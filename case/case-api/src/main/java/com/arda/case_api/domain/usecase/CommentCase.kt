package com.arda.case_api.domain.usecase

import com.arda.case_api.domain.model.Comment

interface CommentCase {
    suspend operator fun invoke(comment: Comment): String
}