package com.arda.case_ui.screens.casedetail

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.arda.auth_ui.R
import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.model.CaseProcessEnum
import com.arda.case_api.domain.model.Comment
import com.arda.case_ui.screens.createcase.components.PhotoPickPopUp
import com.arda.core_api.domain.enums.OfficierSubRoleEnum
import com.arda.core_api.domain.enums.RoleEnum
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_ui.theme.ProjectTheme
import java.time.LocalDate

@Composable
fun CaseDetail(
    onEvent: (CaseDetailEvent) -> Unit,
    state: CaseDetailUiState,
    navController: NavHostController,
) {
    CaseDetailBody(onEvent = onEvent, state = state)
}

@Composable
fun CaseDetailBody(
    onEvent: (CaseDetailEvent) -> Unit,
    state: CaseDetailUiState,
) {
    Box(modifier = Modifier.fillMaxSize(1f), contentAlignment = Alignment.TopCenter) {
        if (state.currentUser!!.role != RoleEnum.user.toString())
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    text = "Resolve Case?",
                    style = MaterialTheme.typography.headlineMedium
                )
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    IconButton(onClick = {
                        onEvent(CaseDetailEvent.resolveCase(CaseProcessEnum.completed))
                    }) {
                        Icon(
                            modifier = Modifier.size(50.dp),
                            imageVector = Icons.Filled.AddTask,
                            tint = Color.Green,
                            contentDescription = ""
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        onEvent(CaseDetailEvent.resolveCase(CaseProcessEnum.failed))

                    }) {
                        Icon(
                            modifier = Modifier.size(50.dp),
                            imageVector = Icons.Filled.Delete,
                            tint = Color.Red,
                            contentDescription = ""
                        )
                    }
                }
            }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.15f))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                state.case?.let { CaseDetailComponent(onEvent, it) }
            }
            item {
                Spacer(modifier = Modifier.fillParentMaxHeight(0.05f))

            }
            state.case?.comments?.forEachIndexed { i, x ->
                item {
                    CommentComponent(x)
                }

            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(1f), contentAlignment = Alignment.BottomCenter) {
        CommentWriteSection(onEvent, state)

    }
}

@Composable
fun ImageScreenPopUp(
    onEvent: (CaseDetailEvent) -> Unit,
    state: CaseDetailUiState,
) {
    if (state.imageShowPopUp) {
        PhotoPickPopUp(addImageEvent = {
            onEvent(CaseDetailEvent.addImage(it))
        }, setShowDialog = {
            onEvent(CaseDetailEvent.switchImagePopUp(it))
        })
    }
}

@Composable
fun CommentWriteSection(
    onEvent: (CaseDetailEvent) -> Unit,
    state: CaseDetailUiState,
) {
    ImageScreenPopUp(onEvent, state)
    Row(
        modifier = Modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        val image by rememberUpdatedState(newValue = state.commentImage)
        IconButton(
            modifier = Modifier
                .size(50.dp),
            //.clip(RoundedCornerShape(20.dp)),
            onClick = {
                onEvent(CaseDetailEvent.switchImagePopUp(true))

            }) {
            Box(modifier = Modifier) {//.clip(RoundedCornerShape(20.dp))
                Icon(
                    modifier = Modifier
                        .fillMaxSize(1f),
//                        .clip(RoundedCornerShape(20.dp)),
                    tint = MaterialTheme.colorScheme.secondaryContainer,
                    imageVector = Icons.Default.Image, contentDescription = null
                )
                Image(
                    modifier = Modifier
                        .fillMaxSize(1f),
//                        .clip(RoundedCornerShape(20.dp))
//                        .border(1.dp, Color.Black)
                    painter = rememberAsyncImagePainter(image),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                )

            }
        }

        var focused by remember { mutableStateOf(false) }
        TextField(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.onTertiary,
                    shape = RoundedCornerShape(20.dp)
                )
                .border(2.dp, Color.Black, shape = RoundedCornerShape(20.dp))
                .onFocusChanged {
                    focused = it.isFocused == true
                }
                .weight(1f),
            enabled = true,
            placeholder = { if (!focused) Text("Type a message") },
            value = state.enteredComment,
            colors = TextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(20.dp),
            onValueChange = { newText ->
                onEvent(CaseDetailEvent.updateComment(newText))
            }
        )
        val focusManager = LocalFocusManager.current
        Button(
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            onClick = {
                focused = false
                focusManager.clearFocus()

                onEvent(CaseDetailEvent.submitComment)
            }) {
            Text(text = "Send", color = Color.White)
        }

    }
}

@Composable
fun LazyItemScope.CommentComponent(comment: Comment) {
    var showImage by remember {
        mutableStateOf(false)
    }
    if (showImage)
        comment.image?.let { ZoomInImage(image = it) { showImage = it } }
    Column(modifier = Modifier) {
//        if (comment.image != null)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillParentMaxHeight(0.3f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painterResource(R.drawable.ic_launcher_foreground),
                "content description",
//                modifier = Modifier.clip(CircleShape),
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .clickable {
                        showImage = true
                    },
                contentScale = ContentScale.Crop
            )

        }
//            Image(
//                modifier = Modifier
//                    .clip(RoundedCornerShape(20.dp)),
//                painter = rememberAsyncImagePainter(comment.image),
//                contentScale = ContentScale.FillBounds,
//                contentDescription = null,
//            )

        Row() {
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(20.dp)),
//                .weight(1f),
                enabled = false,
                value = "${comment.text}",
                colors = TextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(20.dp),
                onValueChange = { newText ->
                }
            )
        }

    }
}

@Composable
fun LazyItemScope.CaseDetailComponent(onEvent: (CaseDetailEvent) -> Unit, case: Case) {
    var showImage by remember {
        mutableStateOf(false)
    }
    if (showImage)
        ZoomInImage(image = case.image) { showImage = it }

    OutlinedCard(
        modifier = Modifier
            .wrapContentHeight()
//            .wrapContentHeight(align = Alignment.Top)
//            .fillParentMaxHeight(0.5f)
            .fillMaxWidth(1f), shape = RoundedCornerShape(20.dp),
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
                Text(
                    modifier = Modifier,
                    text = case.description,
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.titleSmall
                )
                Image(
                    painterResource(R.drawable.ic_launcher_foreground),
                    "content description",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            showImage = true
                        },
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
fun ZoomInImage(image: String, setShowDialog: (Boolean) -> Unit) {
    Dialog(onDismissRequest = {
        setShowDialog.invoke(false)
    }
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(1f),
            color = Color.Transparent // dialog background
        ) {//modifier = Modifier.fillMaxSize(1f)
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .border(5.dp, Color.Black, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(R.drawable.ic_launcher_foreground),
                    "content description",
                    modifier = Modifier
//                        .clip(RoundedCornerShape(20.dp))
                        .fillMaxSize(1f),
                    contentScale = ContentScale.FillBounds
                )
            }

        }
    }
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
                    comments = listOf(
                        Comment(
                            userID = "asf",
                            userName = "Bertha Schmidt",
                            caseID = "dolorem",
                            text = "suscipiantur",
                            image = null
                        )
                    )
                ),
                enteredComment = "it is resvoledresvoledresvoled",
                commentImage = null,
                imageShowPopUp = false
            )
        )
    }
}