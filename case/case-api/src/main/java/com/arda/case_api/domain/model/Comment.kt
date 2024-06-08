package com.arda.case_api.domain.model

data class Comment(
    val userID : String,
    val userName : String,
    val caseID: String,
    val text: String,
    val image : String? = null,
)
