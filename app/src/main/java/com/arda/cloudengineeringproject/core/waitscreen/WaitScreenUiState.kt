package com.arda.cloudengineeringproject.core.waitscreen

enum class PossibleNavigations(){
    NAN,
    AUTH_SCREEN,
    HOME_SCREEN
}
data class WaitScreenUiState(
    val navigatedScreen : PossibleNavigations = PossibleNavigations.NAN
)