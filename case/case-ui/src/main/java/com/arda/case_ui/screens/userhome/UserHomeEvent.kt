package com.arda.case_ui.screens.userhome

sealed class UserHomeEvent {
    data class selectUserCase(val caseID : String) : UserHomeEvent()
    data class selectRole(val caseID : String,val role : String) : UserHomeEvent()
    data class removeCase(val caseID : String) : UserHomeEvent()
    object listUserCases : UserHomeEvent()
}