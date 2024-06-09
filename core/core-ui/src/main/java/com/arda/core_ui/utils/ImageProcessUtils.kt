package com.arda.core_ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

object ImageProcessUtils {

    lateinit var savedContext : Context
    fun initImageProcess(context: Context){
        savedContext = context
    }
    fun scaleBitmapDown(bitmap: Bitmap, maxDimension: Int): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height
        var resizedWidth = maxDimension
        var resizedHeight = maxDimension
        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension
            resizedWidth =
                (resizedHeight * originalWidth.toFloat() / originalHeight.toFloat()).toInt()
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension
            resizedHeight =
                (resizedWidth * originalHeight.toFloat() / originalWidth.toFloat()).toInt()
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension
            resizedWidth = maxDimension
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false)
    }

    fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {

        val baos = ByteArrayOutputStream()
        val scaledBitMap = scaleBitmapDown(bitmap, 320)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos)
        val data = baos.toByteArray();
        return data
    }

    fun convertByteArrayToBitmap(byteArray: ByteArray): Bitmap? {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size);
        return bitmap
    }

    fun convertBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        val base64encoded = Base64.encodeToString(imageBytes, Base64.NO_WRAP)
        return base64encoded
    }

    fun convertUriToBitmap(uri: Uri): Bitmap {
        var bitmap: Bitmap
        uri!!.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images.Media.getBitmap(savedContext.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(savedContext.contentResolver, it)
                bitmap = ImageDecoder.decodeBitmap(source)
            }
        }
        return bitmap
    }

    fun Bitmap.BitmaptoBase64(): String {
        val baos = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT).filter { !it.isWhitespace() }
    }
    // convert Base64 to Image bitmap
    fun String.Base64toBitmap(): Bitmap {
        val decodedBytes = Base64.decode(this, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
    fun urlToBitmap(
        scope: CoroutineScope,
        imageURL: String,
        context: Context,
        onSuccess: (bitmap: Bitmap) -> Unit,
        onError: (error: Throwable) -> Unit
    ) {
        var bitmap: Bitmap? = null
        val loadBitmap = scope.launch(Dispatchers.IO) {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(imageURL)
                .allowHardware(false)
                .build()
            val result = loader.execute(request)
            if (result is SuccessResult) {
                bitmap = (result.drawable as BitmapDrawable).bitmap
            } else if (result is ErrorResult) {
                cancel(result.throwable.localizedMessage ?: "ErrorResult", result.throwable)
            }
        }
        loadBitmap.invokeOnCompletion { throwable ->
            bitmap?.let {
                onSuccess(it)
            } ?: throwable?.let {
                onError(it)
            } ?: onError(Throwable("Undefined Error"))
        }
    }
}