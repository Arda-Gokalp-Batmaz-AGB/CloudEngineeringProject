package com.arda.case_api.domain.model

data class Case(
    val id: String,
    val image : String,
    val description : String,
    val location: CaseLocation,
    )
