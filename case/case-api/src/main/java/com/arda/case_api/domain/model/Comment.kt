package com.arda.case_api.domain.model

data class Comment(
    val username: String,
    val caseID: String,
    val text: String,
)
