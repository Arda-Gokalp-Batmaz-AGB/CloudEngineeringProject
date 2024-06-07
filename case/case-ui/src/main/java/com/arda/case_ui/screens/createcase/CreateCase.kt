package com.arda.case_ui.screens.createcase

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.model.CategoryEnum
import com.arda.case_api.domain.model.getAllCaseCategories
import com.arda.case_ui.components.GetSnackBarChoiceCallBack
import com.arda.case_ui.screens.createcase.components.PhotoPickPopUp
import com.arda.case_ui.screens.createcase.components.QrCodeAnalyzer
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_ui.components.providers.LocalSnackbarHostState
import com.arda.core_ui.nav.NavItem
import com.arda.core_ui.theme.ProjectTheme

private val TAG = DebugTagsEnumUtils.UITag.tag

@Composable
fun CreateCase(
    onEvent: (CreateCaseEvent) -> Unit,
    state: CreateCaseUiState,
    navController: NavHostController,
) {
    val location: CaseLocation? by rememberUpdatedState(newValue = state.location)
    if (location != null) {
        CaseFormBody(onEvent = onEvent, state = state,navController=navController)
    } else {
        QRScreen(onEvent = onEvent, state = state)
    }
}

@Composable
fun CaseFormBody(
    onEvent: (CreateCaseEvent) -> Unit,
    state: CreateCaseUiState,
    navController: NavHostController
) {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
//        .fillMaxHeight(1f)
            .fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxHeight(0.1f),
            text = "Create a New Case",
            style = MaterialTheme.typography.headlineLarge
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.9f),
//                .matchParentSize()
//                .verticalScroll(
//                    scroll
//                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                ImageSection(onEvent = onEvent, state = state)
            }
            item {
                HeaderDropDown(onEvent, state)
            }
            item {
                Text(
                    modifier = Modifier.fillParentMaxHeight(0.1f),
                    text = "Location",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            item {
                LocationSection(onEvent, state)
            }
            item {
                Spacer(modifier = Modifier.fillParentMaxHeight(0.05f))
                Text(
                    modifier = Modifier.fillParentMaxHeight(0.1f),
                    text = "Additional Info",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            item {
                FormTextField(
                    label = "Description", state.description
                ) { onEvent(CreateCaseEvent.updateDescription(it)) }
            }
            item {
                Spacer(modifier = Modifier.fillParentMaxHeight(0.1f))

            }

            item {
                submitForm(onEvent = onEvent, navController = navController)
            }
        }
    }
}

@Composable
fun submitForm(onEvent: (CreateCaseEvent) -> Unit,navController: NavHostController) {
    val snackbarHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()
    val snackbarCallback =
        GetSnackBarChoiceCallBack(snackbarHostState, "Case is Sucessfully Created", scope)
    Button(
        modifier = Modifier,
        shape = RoundedCornerShape(15.dp),
        enabled = true,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        onClick = {
            onEvent(CreateCaseEvent.submitForm(snackbarCallback){
                navController.navigate(NavItem.Home.route) {
                    launchSingleTop = true
                }
            })
        }) {
        Text(
            text = "Submit",
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.titleMedium
        )
        Icon(
            Icons.Filled.Send,
            contentDescription = "",
            modifier = Modifier.size(ButtonDefaults.IconSize),
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun LazyItemScope.FormTextField(
    label: String,
    text: String,
    event: (String) -> Unit,
) {
    Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.3f),
                    text = label,
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.titleMedium
                )

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
                placeholder = { if (!focused) Text(label) },
                label = { Text((label), color = Color.Black) },
                value = text,
                colors = TextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(20.dp),
                onValueChange = { newText ->
                    event.invoke(newText)
                }
            )
        }

    }
}

@Composable
fun LazyItemScope.LocationSection(
    onEvent: (CreateCaseEvent) -> Unit,
    state: CreateCaseUiState,
) {
    val location by rememberUpdatedState(newValue = state.location)
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        AutoFilledTextField(label = "Address:", text = location?.address)
        AutoFilledTextField(label = "Place:", text = location?.place)
        AutoFilledTextField(label = "Building:", text = location?.building)
        AutoFilledTextField(label = "Floor:", text = location?.floor)
    }
}

@Composable
fun ColumnScope.AutoFilledTextField(
    label: String,
    text: String?,
) {
    text?.let {
        if (text != "" && text != " ")
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                    Text(
                        modifier = Modifier.fillMaxWidth(0.3f),
                        text = label,
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.titleMedium
                    )

                }
                TextField(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onTertiary,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(20.dp))
                        .weight(1f),
                    enabled = false,
                    value = text,
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
fun LazyItemScope.HeaderDropDown(onEvent: (CreateCaseEvent) -> Unit, state: CreateCaseUiState) {
    var dropControl by remember { mutableStateOf(false) }
    val selectedCategory by rememberUpdatedState(newValue = state.selectedCategory)
    val categoryList by remember {
        mutableStateOf(getAllCaseCategories())
    }


    OutlinedCard(
        modifier = Modifier
            .padding(16.dp)
            .background(
                MaterialTheme.colorScheme.onTertiary,
                shape = RoundedCornerShape(20.dp)
            )
            .border(2.dp, Color.Black, shape = RoundedCornerShape(20.dp))
    ) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
//                .width(300.dp)
//                .height(50.dp)
                .fillParentMaxWidth(0.6f)
//                .fillParentMaxHeight(0.06f)
                .padding(5.dp)
                .clickable {
                    dropControl = true
                }) {

            Text(text = selectedCategory, style = MaterialTheme.typography.headlineMedium)
            Icon(
                Icons.Filled.ArrowDropDown,
                contentDescription = ""
            )

        }
        DropdownMenu(expanded = dropControl, onDismissRequest = { dropControl = false }) {

            categoryList.forEach { header ->
                DropdownMenuItem(
                    text = { Text(text = header) },
                    onClick = {
                        dropControl = false
                        onEvent(CreateCaseEvent.setHeader(header))
                    })
            }

        }

    }
}

@Composable
fun LazyItemScope.ImageSection(onEvent: (CreateCaseEvent) -> Unit, state: CreateCaseUiState) {
    ImageScreenPopUp(onEvent, state)
    val image by rememberUpdatedState(newValue = state.image)
    Log.v(TAG, "IMAGE : ${image}")
    Box(
        modifier = Modifier
            .fillParentMaxWidth()
            .fillParentMaxHeight(0.4f)
            .clip(RoundedCornerShape(20.dp))
            .border(5.dp, Color.Black),
        contentAlignment = Alignment.Center
    ) {

        if (image != null)
            Image(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .clip(RoundedCornerShape(20.dp)),
                painter = rememberAsyncImagePainter(image),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
            )
        else
            IconButton(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .clip(RoundedCornerShape(20.dp)),
                onClick = {
                    onEvent(CreateCaseEvent.switchImagePopUp(true))

                }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(1f)
                        .clip(RoundedCornerShape(20.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize(0.8f)
                            .clip(RoundedCornerShape(20.dp)),
                        imageVector = Icons.Default.Image, contentDescription = null
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize(1f)
                            .clip(RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "Upload Image",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                }


            }

    }

}

@Composable
fun QRScreen(
    onEvent: (CreateCaseEvent) -> Unit,
    state: CreateCaseUiState,
) {
    var code by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCamPermission = granted
        }
    )
    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (hasCamPermission) {
            AndroidView(
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(
                            Size(
                                previewView.width,
                                previewView.height
                            )
                        )
                        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        QrCodeAnalyzer { result ->
                            code = result
                        }
                    )
                    try {
                        cameraProviderFuture.get().bindToLifecycle(
                            lifecycleOwner,
                            selector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    previewView
                },
                modifier = Modifier.weight(1f)
            )
            if (code.isNotEmpty())
                LaunchedEffect(key1 = code) {
                    onEvent(CreateCaseEvent.fillQRCode(code))
                }
            Text(
                text = code,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            )
        }
    }
}

//@Composable
//fun LazyItemScope.UploadImage(
//    onEvent: (CreateCaseEvent) -> Unit,
//    state: CreateCaseUiState,
//) {
//    ImageScreenPopUp(onEvent, state)
//    Button(
//        modifier = Modifier
//            .fillParentMaxWidth(0.8f),
//        shape = RoundedCornerShape(15.dp),
//        enabled = true,
//        colors = ButtonDefaults.buttonColors(
//            containerColor = MaterialTheme.colorScheme.secondary
//        ),
//        onClick = {
//            onEvent(CreateCaseEvent.switchImagePopUp(true))
//
//        }) {
//        Text(text = "Upload Image", color = MaterialTheme.colorScheme.onSecondary)
//        Icon(
//            Icons.Filled.Login,
//            contentDescription = "",
//            modifier = Modifier.size(ButtonDefaults.IconSize),
//            tint = MaterialTheme.colorScheme.onSecondary
//        )
//    }
//}

@Composable
fun ImageScreenPopUp(
    onEvent: (CreateCaseEvent) -> Unit,
    state: CreateCaseUiState,
) {
    if (state.imageShowPopUp) {
        PhotoPickPopUp(onEvent, state, setShowDialog = {
            onEvent(CreateCaseEvent.switchImagePopUp(it))
        })
    }
}

@Composable
@androidx.compose.ui.tooling.preview.Preview
fun previewCreateCase() {
    ProjectTheme {
        CaseFormBody(
            onEvent = {}, state = CreateCaseUiState(
                currentUser = null,
                addressText = "ea",
                description = "consetetur",
                location = CaseLocation("asfsafsaf", "asfasfsaf", "adsfasfsaf", "asfasfsaf"),
                categoryEnum = CategoryEnum.cleaning,
                image = null,
                imageShowPopUp = false
            ),
            rememberNavController()
        )
    }
}