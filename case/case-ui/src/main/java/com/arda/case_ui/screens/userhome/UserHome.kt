package com.arda.case_ui.screens.userhome

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.model.CaseProcessEnum
import com.arda.core_ui.nav.NavItem
import com.arda.core_ui.theme.ProjectTheme
import java.time.LocalDate

@Composable
fun UserHome(
    onEvent: (UserHomeEvent) -> Unit,
    state: UserHomeUiState,
    navController: NavHostController,
) {
    val caseList by rememberUpdatedState(newValue = state.caseList)
    RedirectCaseDetails(state = state, navController = navController)
    Column(
        modifier = Modifier.fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxHeight(0.1f),
            text = "Active Cases",
            style = MaterialTheme.typography.headlineLarge
        )
        val scrollState = rememberScrollState()
        LazyColumn(
            Modifier
                .fillMaxWidth(0.8f)
            //    .fillMaxSize(1f)
            , horizontalAlignment = Alignment.CenterHorizontally
//                .verticalScroll(state=scrollState,true)
        ) {
            caseList.forEach { x ->
                item {
                    CaseComponent(onEvent, x)
                    Spacer(modifier = Modifier.fillParentMaxHeight(0.06f))
                }
            }
        }

    }
}

@Composable
fun RedirectCaseDetails(state: UserHomeUiState,navController: NavHostController){
    val selectedCaseID by rememberUpdatedState(newValue = state.selectedCaseID)
    if(selectedCaseID != ""){
        LaunchedEffect(key1 = true){
            navController.navigate(NavItem.CaseDetail.route + "/$selectedCaseID") {
                launchSingleTop = true
            }
        }
    }
}
@Composable
fun LazyItemScope.CaseComponent(onEvent: (UserHomeEvent) -> Unit, case: Case) {
    OutlinedCard(
        modifier = Modifier
            .fillParentMaxHeight(0.2f)
            .fillMaxWidth(1f)
            .clickable {
                onEvent(UserHomeEvent.selectUserCase(case.id))
            }, shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )


    ) {
        Row(modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = case.header, style = MaterialTheme.typography.headlineMedium)
                Row {
                    Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "")
                    Text(text = case.location.place, style = MaterialTheme.typography.titleSmall)
                }
                Row() {
                    Icon(imageVector = Icons.Filled.Timer, contentDescription = "")
                    Text(text = "09/06/2024", style = MaterialTheme.typography.titleSmall)
                    //todo düzelt
                }
                Row() {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                    Text(text = case.userName, style = MaterialTheme.typography.titleSmall)
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(com.arda.auth_ui.R.drawable.ic_launcher_foreground),
                    "content description",
                    modifier = Modifier.clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = case.currentProcess.processName,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

//    Card(){
//
//    }
}

@Preview
@Composable
fun previewUserHome() {
    ProjectTheme {
        UserHome(
            onEvent = {}, state = UserHomeUiState(
                currentUser = null,
                selectedCaseID = "",
                caseList = listOf(
                    Case(
                        id = "dictas",
                        userName = "Keith Dickerson",
                        assignedOfficerSubRole = null,
                        currentProcess = CaseProcessEnum.waiting_for_response,
                        image = "feugait",
                        header = "penatibus",
                        time = LocalDate.now(),
                        description = "tortor",
                        location = CaseLocation(
                            address = "viris",
                            place = "quod",
                            building = "hendrerit",
                            floor = "docendi"
                        ),
                        comments = listOf()
                    ),
                    Case(
                        id = "utamur",
                        userName = "Simone Shaffer",
                        assignedOfficerSubRole = null,
                        currentProcess = CaseProcessEnum.waiting_for_response,
                        image = "elitr",
                        header = "vidisse",
                        time = LocalDate.now(),
                        description = "graeco",
                        location = CaseLocation(
                            address = "singulis",
                            place = "persius",
                            building = "ad",
                            floor = "dicam"
                        ),
                        comments = listOf()
                    )
                )
            ), navController = rememberNavController()
        )
    }

}