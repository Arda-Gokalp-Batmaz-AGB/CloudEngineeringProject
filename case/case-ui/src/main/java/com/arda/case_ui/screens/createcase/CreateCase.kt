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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.model.CategoryEnum
import com.arda.case_api.domain.model.getAllCaseCategories
import com.arda.case_ui.screens.createcase.components.PhotoPickPopUp
import com.arda.case_ui.screens.createcase.components.QrCodeAnalyzer
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_ui.theme.ProjectTheme

private val TAG = DebugTagsEnumUtils.UITag.tag

@Composable
fun CreateCase(
    onEvent: (CreateCaseEvent) -> Unit,
    state: CreateCaseUiState,
    navController: NavController,
) {
    val location: CaseLocation? by rememberUpdatedState(newValue = state.location)
    if (location != null) {
        CaseFormBody(onEvent = onEvent, state = state)
    } else {
        QRScreen(onEvent = onEvent, state = state)
    }
}

@Composable
fun CaseFormBody(
    onEvent: (CreateCaseEvent) -> Unit,
    state: CreateCaseUiState,
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
            modifier = Modifier,
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
//                UploadImage(onEvent = onEvent, state = state)

            }
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


    OutlinedCard(modifier = Modifier.padding(16.dp)) {
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
                location = null,
                categoryEnum = CategoryEnum.cleaning,
                image = null,
                imageShowPopUp = false
            )
        )
    }
}