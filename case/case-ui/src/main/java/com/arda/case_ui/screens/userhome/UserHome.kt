package com.arda.case_ui.screens.userhome

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.model.CaseProcessEnum
import com.arda.core_api.domain.enums.OfficierSubRoleEnum
import com.arda.core_api.domain.enums.RoleEnum
import com.arda.core_api.domain.enums.getAllRolesExcludingOfficer
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_ui.nav.NavItem
import com.arda.core_ui.theme.ProjectTheme
import com.arda.core_ui.theme.parent_background
import java.time.LocalDate


private val TAG = DebugTagsEnumUtils.UITag.tag

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
        Text(
            modifier = Modifier.fillMaxHeight(0.1f),
            text = "Your Role: ${state.currentUser!!.role}",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        var firstButtonIsSelectedButton by remember {
            mutableStateOf(true)
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = { firstButtonIsSelectedButton = true }, colors =
                ButtonDefaults.buttonColors(
                    containerColor = if (firstButtonIsSelectedButton) Color(
                        0xFF00897B
                    ) else MaterialTheme.colorScheme.background
                )
            ) {
                Text("List All Cases", color = MaterialTheme.colorScheme.tertiary)
            }
            Button(
                onClick = {
                    firstButtonIsSelectedButton = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!firstButtonIsSelectedButton) Color(0xFF00897B) else MaterialTheme.colorScheme.background
                )
            ) {
                Text("List Resolved Cases", color = MaterialTheme.colorScheme.tertiary)
            }
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        val scrollState = rememberScrollState()
        if (state.isLoading == false) {
            LazyColumn(
                Modifier
                    .fillMaxWidth(0.8f)
                //    .fillMaxSize(1f)
                , horizontalAlignment = Alignment.CenterHorizontally
//                .verticalScroll(state=scrollState,true)
            ) {
                if(firstButtonIsSelectedButton)
                caseList.forEach { x ->
                    item {
//                    val selectedRoles by rememberUpdatedState(newValue = state.selectedRole)
                        CaseComponent(onEvent, x, state)
                        Spacer(modifier = Modifier.fillParentMaxHeight(0.06f))
                    }
                }
                else
                    caseList.filter { x-> x.currentProcess == CaseProcessEnum.completed }.forEach { x ->
                        item {
//                    val selectedRoles by rememberUpdatedState(newValue = state.selectedRole)
                            CaseComponent(onEvent, x, state)
                            Spacer(modifier = Modifier.fillParentMaxHeight(0.06f))
                        }
                    }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(1f), contentAlignment = Alignment.Center) {
                Text("Loading...")
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(0.5f),
                    color = Color.Magenta
                )

            }
        }

    }
}

@Composable
fun RedirectCaseDetails(state: UserHomeUiState, navController: NavHostController) {
    val selectedCaseID by rememberUpdatedState(newValue = state.selectedCaseID)
    if (selectedCaseID != "") {
        LaunchedEffect(key1 = true) {
            navController.navigate(NavItem.CaseDetail.route + "/$selectedCaseID") {
                launchSingleTop = true
            }
        }
    }
}

@Composable
fun LazyItemScope.CaseComponent(
    onEvent: (UserHomeEvent) -> Unit,
    case: Case,
    state: UserHomeUiState,
) {
    OutlinedCard(
        modifier = Modifier
//            .fillParentMaxHeight(0.3f)
            .fillMaxWidth(1f)
            .clickable {
                onEvent(UserHomeEvent.selectUserCase(case.id))
            }, shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = parent_background,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )

    ) {
        Column() {
            Row(modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = case.header, style = MaterialTheme.typography.headlineSmall)
                    Row {
                        Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "")
                        Text(
                            text = case.location.building.take(20) + "...",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Row() {
                        Icon(imageVector = Icons.Filled.Timer, contentDescription = "")
                        Text(
                            text = case.time.toString(),
                            style = MaterialTheme.typography.titleSmall
                        )
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
//                    Image(
//                        painterResource(com.arda.auth_ui.R.drawable.ic_launcher_foreground),
//                        "content description",
//                        modifier = Modifier.clip(CircleShape),
//                        contentScale = ContentScale.Crop
//                    )
                    AsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop,
                        model = case.image,
                        contentDescription = "image",
                    )
                    val situationColor by remember {
                        val color = if (case.currentProcess == CaseProcessEnum.completed) {
                            Color(0xFF00897B)
                        } else if (case.currentProcess == CaseProcessEnum.on_process) {
                            Color(0xFFD3D062)
                        } else {
                            Color(0xFF010101)
                        }
                        mutableStateOf(color)
                    }
                    Text(
                        textAlign = TextAlign.Center,
                        text = case.currentProcess.processName,
                        style = MaterialTheme.typography.bodySmall,
                        color = situationColor
                    )
                }
            }
            if (state.currentUser?.role == RoleEnum.admin.role) {
                RoleAssignSection(onEvent = onEvent, case = case, state = state)
            }
        }
    }

//    Card(){
//
//    }
}

@Composable
fun RoleAssignSection(onEvent: (UserHomeEvent) -> Unit, case: Case, state: UserHomeUiState) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Filled.Assignment,
            contentDescription = ""
        )
        RoleDropDown(onEvent = onEvent, case = case, state = state)
        Spacer(modifier = Modifier.weight(1f))
        if (state.currentUser!!.role == RoleEnum.admin.role)
            IconButton(onClick = { onEvent(UserHomeEvent.removeCase(case.id)) }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "",
                    tint = Color.Red
                )

            }
//        Text(text = "09/06/2024", style = MaterialTheme.typography.titleSmall)
        //todo düzelt
    }
}

@Composable
fun RoleDropDown(onEvent: (UserHomeEvent) -> Unit, case: Case, state: UserHomeUiState) {
    var dropControl by remember { mutableStateOf(false) }
//    val selectedRole by rememberUpdatedState(newValue = state.selectedRole)
    //val selectedRole : String = CategoryEnum.empty.categoryName,
    val roleList by remember {
        mutableStateOf(getAllRolesExcludingOfficer().filter { x -> x != "User" && x != "Admin" })
    }
    var selectedRole by remember {//
        mutableStateOf(case.assignedOfficerSubRole?.role ?: RoleEnum.empty.role)
    }
    Log.v(TAG, "SELECTEDROLE:${selectedRole}")
    OutlinedCard(
        modifier = Modifier
            .padding(16.dp)
            .background(
                parent_background,
                shape = RoundedCornerShape(20.dp)
            )
            .border(2.dp, Color.Black, shape = RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = parent_background,
        ),
    ) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
//                .fillParentMaxWidth(0.6f)
                .padding(5.dp)
                .clickable {
                    dropControl = true
                }) {

            Text(text = selectedRole, style = MaterialTheme.typography.headlineSmall)
            Icon(
                Icons.Filled.ArrowDropDown,
                contentDescription = ""
            )

        }
        DropdownMenu(expanded = dropControl, onDismissRequest = { dropControl = false }) {

            roleList.forEach { role ->
                DropdownMenuItem(
                    text = { Text(text = role) },
                    onClick = {
                        dropControl = false
                        selectedRole = role
                        onEvent(UserHomeEvent.selectRole(case.id, role))
                    })
            }

        }

    }
}

@Preview
@Composable
fun previewUserHome() {
    ProjectTheme {
        UserHome(
            onEvent = {}, state = UserHomeUiState(
                currentUser = MinimizedUser(
                    uid = "JEVjD8jofMY8UmsPozfhTKE4Mey1",
                    role = "Admin",
                    email = "safsaf"
                ),
                selectedCaseID = "",
                caseList = listOf(
                    Case(
                        id = "dictas",
                        userID = "JEVjD8jofMY8UmsPozfhTKE4Mey1",
                        userName = "admin",
                        assignedOfficerSubRole = null,
                        currentProcess = CaseProcessEnum.waiting_for_response,
                        image = "feugait",
                        header = "penatibus",
                        time = LocalDate.of(2024, 5, 5),
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
                        userID = "JEVjD8jofMY8UmsPozfhTKE4Mey1",
                        userName = "admin",
                        assignedOfficerSubRole = OfficierSubRoleEnum.gardener,
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