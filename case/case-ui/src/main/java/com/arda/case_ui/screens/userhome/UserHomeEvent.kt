package com.arda.case_ui.screens.userhome

sealed class UserHomeEvent {
    data class selectUserCase(val caseID : String) : UserHomeEvent()
    object listUserCases : UserHomeEvent()
}