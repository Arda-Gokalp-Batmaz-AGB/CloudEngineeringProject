package com.arda.core_api.validation
data class ValidationResult(
    val isValid : Boolean,
    val exception: Exception,
)