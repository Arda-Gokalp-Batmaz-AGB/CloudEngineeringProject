package com.arda.case_ui.screens.casedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arda.auth_ui.R
import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.model.CaseProcessEnum
import com.arda.case_ui.screens.userhome.UserHomeEvent
import com.arda.core_api.domain.enums.OfficierSubRoleEnum
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_ui.theme.ProjectTheme
import java.time.LocalDate

@Composable
fun CaseDetail(
    onEvent: (CaseDetailEvent) -> Unit,
    state: CaseDetailUiState,
    navController: NavHostController,
) {

}
@Composable
fun CaseDetailBody(
    onEvent: (CaseDetailEvent) -> Unit,
    state: CaseDetailUiState,
){
    Column(
        modifier = Modifier
            .fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxHeight(0.1f),
            text = "Case Details",
            style = MaterialTheme.typography.headlineLarge
        )
        Column(modifier=Modifier.fillMaxWidth(0.5f).padding(vertical = 10.dp), horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = "Resolve Case?",
                style = MaterialTheme.typography.headlineMedium
            )
            Row(horizontalArrangement = Arrangement.SpaceAround){
            IconButton(onClick = {
                onEvent(CaseDetailEvent.resolveCase(CaseProcessEnum.completed))
            }) {
                Icon(modifier=Modifier.size(50.dp),imageVector = Icons.Filled.AddTask, tint = Color.Green, contentDescription = "")
            }
                Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                onEvent(CaseDetailEvent.resolveCase(CaseProcessEnum.failed))

            }) {
                Icon(modifier=Modifier.size(50.dp),imageVector = Icons.Filled.Delete, tint = Color.Red, contentDescription ="" )
            }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                state.case?.let { CaseDetailComponent(onEvent, it) }
            }
        }
    }
}

@Composable
fun LazyItemScope.CaseDetailComponent(onEvent: (CaseDetailEvent) -> Unit, case: Case) {
    OutlinedCard(
        modifier = Modifier
            .wrapContentHeight()
//            .wrapContentHeight(align = Alignment.Top)
//            .fillParentMaxHeight(0.5f)
            .fillMaxWidth(1f)
            .clickable {
//                onEvent(UserHomeEvent.selectUserCase(case.id))
            }, shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )


    ) {
        Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)) {
            Column(
                modifier = Modifier,//.weight(1f),
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
                Row() {
                    Icon(imageVector = Icons.Filled.Description, contentDescription = "")
                    Text(text = "Description:", style = MaterialTheme.typography.titleSmall)
                }
            }
            Column(
                modifier = Modifier,//.weight(1f)
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Row() {
//                    Icon(imageVector = Icons.Filled.Description, contentDescription = "")
//                    Text(modifier=Modifier.verticalScroll(rememberScrollState()),text = case.description, style = MaterialTheme.typography.titleSmall)
//                }//.verticalScroll(rememberScrollState())
                Text(modifier=Modifier,text = case.description, textAlign = TextAlign.Left, style = MaterialTheme.typography.titleSmall)
                Image(
                    painterResource(R.drawable.ic_launcher_foreground),
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
@Composable
@Preview
fun previewCaseDetail() {
    ProjectTheme {
        CaseDetailBody(
            onEvent = {}, state = CaseDetailUiState(
                currentUser = MinimizedUser(
                    uid = "verear",
                    role = "malorum",
                    email = "bennett.hurst@example.com"
                ),
                loading = false,
                case = Case(
                    id = "odio",
                    userName = "Beverley Carson",
                    assignedOfficerSubRole = OfficierSubRoleEnum.gardener,
                    currentProcess = CaseProcessEnum.waiting_for_response,
                    image = "perpetua",
                    header = "homero",
                    time = LocalDate.now(),
                    description = "senserit senssens eritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseriteritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenseritsenserit",
                    location = CaseLocation(
                        address = "tractatos",
                        place = "cubilia",
                        building = "assueverit",
                        floor = "homero"
                    ),
                    comments = listOf()
                ),
                comment = "it is resvoled",
                image = null,
                imageShowPopUp = false
            )
        )
    }
}