package com.arda.case_ui.screens.createcase.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.arda.case_ui.screens.createcase.CreateCaseEvent
import com.arda.case_ui.screens.createcase.CreateCaseUiState
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_ui.utils.ComposeFileProviderUtils

private val TAG = DebugTagsEnumUtils.UITag.tag

enum class PhotoTypeEnum {
    GALLERY,
    CAMERA,
    NAN
};
@Composable
fun PhotoPickPopUp(
    addImageEvent: (Bitmap) -> Unit,
    setShowDialog: (Boolean) -> Unit,
) {
    var pickedPhotoType by remember { mutableStateOf(PhotoTypeEnum.NAN) }
    PickImageFromGallery(addImageEvent, pickedPhotoType) { value -> pickedPhotoType = value }
    PickImageFromCamera(addImageEvent,  pickedPhotoType) { value -> pickedPhotoType = value }
    Dialog(onDismissRequest = {
        setShowDialog.invoke(false)
    }
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.92f),
            color = Color.Transparent // dialog background
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                // text and buttons
                Column(
                    modifier = Modifier
                        .padding(top = 30.dp) // this is the empty space at the top
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(percent = 10)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(height = 36.dp))

                    Text(
                        text = "Pick A Image",
                        fontSize = 24.sp,
                        //color = colorResource(id = textColor)
                    )

                    Spacer(modifier = Modifier.height(height = 5.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        GalleryPopUP(addImageEvent, pickedPhotoType) { value ->
                            pickedPhotoType = value
                        }
                        CameraPopUP(addImageEvent, pickedPhotoType) { value ->
                            pickedPhotoType = value
                        }
                        PickImageFromGallery(
                            addImageEvent,
                            pickedPhotoType
                        ) { value -> pickedPhotoType = value }
                        PickImageFromCamera(
                            addImageEvent,
                            pickedPhotoType
                        ) { value -> pickedPhotoType = value }
                    }

                    Spacer(modifier = Modifier.height(height = 5.dp * 2))
                }

                // delete icon
                Icon(
                    imageVector = Icons.Filled.ImageSearch,
                    contentDescription = "Delete Icon",
                    //tint = positiveButtonColor,
                    modifier = Modifier
                        .background(color = Color.White, shape = CircleShape)
                        //  .border(width = 2.dp, shape = CircleShape, color = positiveButtonColor)
                        .padding(all = 16.dp)
                        .align(alignment = Alignment.TopCenter)
                )
            }
        }
    }

}

@Composable
fun CameraPopUP(
    addImageEvent: (Bitmap) -> Unit,
    pickedPhotoType: PhotoTypeEnum,
    setPhotoType: (value: PhotoTypeEnum) -> Unit,
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(0.dp),
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Camera",
                //   color = colorResource(id = textColor)
            )
            IconButton(onClick = {
//                addImageEvent(StoryEvent.switchStoryPopUp(false))
                setPhotoType(PhotoTypeEnum.CAMERA)
            }) {
                Icon(
                    imageVector = Icons.Filled.PhotoCamera,
                    contentDescription = "Camera",
                    modifier = Modifier.fillMaxSize(),
                    tint = MaterialTheme.colorScheme.secondary
                )

            }
        }

    }
}

@Composable
fun GalleryPopUP(
    addImageEvent: (Bitmap) -> Unit,
    pickedPhotoType: PhotoTypeEnum,
    setPhotoType: (value: PhotoTypeEnum) -> Unit,
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(0.dp),
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Gallery",
                // color = colorResource(id = textColor)
            )
            IconButton(onClick = {
//                addImageEvent(StoryEvent.switchStoryPopUp(false))
                setPhotoType(PhotoTypeEnum.GALLERY)
            }) {
                Icon(
                    imageVector = Icons.Filled.Image,
                    contentDescription = "Gallery",
                    modifier = Modifier.fillMaxSize(),
                    tint = MaterialTheme.colorScheme.secondary
                )

            }
        }

    }
}

@Composable
fun PickImageFromCamera(
    addImageEvent: (Bitmap) -> Unit,
    pickedPhotoType: PhotoTypeEnum,
    setPhotoType: (value: PhotoTypeEnum) -> Unit,
) {
    val context = LocalContext.current

    var hasImage by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
            if (success == true) {
                imageUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        addImageEvent(
                            MediaStore.Images.Media.getBitmap(
                                context.contentResolver,
                                it
                            )
                        )
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, it)
                        addImageEvent(ImageDecoder.decodeBitmap(source))
                    }
                }
                // model.getImageLabels()
            }

        }
    )
    if (pickedPhotoType == PhotoTypeEnum.CAMERA) {
        val uri = ComposeFileProviderUtils(context)
        imageUri = uri
        cameraLauncher.launch(uri)
        setPhotoType(PhotoTypeEnum.NAN)
    }


}

@Composable
fun PickImageFromGallery(
    addImageEvent: (Bitmap) -> Unit,
    pickedPhotoType: PhotoTypeEnum,
    setPhotoType: (value: PhotoTypeEnum) -> Unit,
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        Log.v(TAG, "Current Map:${uri}")
        imageUri = uri
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                addImageEvent(
                    MediaStore.Images.Media.getBitmap(
                        context.contentResolver,
                        it
                    )
                )
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                addImageEvent(ImageDecoder.decodeBitmap(source))
            }
            //model.getImageLabels()
        }
    }
    if (pickedPhotoType == PhotoTypeEnum.GALLERY) {
        launcher.launch("image/*")
        setPhotoType(PhotoTypeEnum.NAN)
    }
}