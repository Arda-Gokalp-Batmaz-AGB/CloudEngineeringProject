package com.arda.core_ui.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.arda.auth_ui.R
import java.io.File

class ComposeFileProviderUtils : FileProvider(
    R.xml.filepaths
) {
    companion object {
        operator fun invoke(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory,
            )
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}
