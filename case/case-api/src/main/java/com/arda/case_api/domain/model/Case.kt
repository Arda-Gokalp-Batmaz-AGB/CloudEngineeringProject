package com.arda.case_api.domain.model

import com.arda.core_api.domain.enums.OfficierSubRoleEnum

data class Case(
    val id: String,
    val userName: String,
    val assignedOfficerSubRole: OfficierSubRoleEnum? = null,
    val currentProcess: CaseProcessEnum,
    val image: String,
    val header: String,
    val time: Long,
    val description: String,
    val location: CaseLocation,
    val comments: List<Comment> = listOf(),
)

enum class CaseProcessEnum(val processName: String) {
    on_process("On Process"),
    completed("Completed"),
    failed("Failed"),
    waiting_for_response("Waiting for response")
}
enum class CategoryEnum(val categoryName : String){
    empty(""),
    electric("Electrical"),
    trash("Trash"),
    office_suplies("Office Suplies"),
    lighting("Lighting"),
    other("Other"),
}