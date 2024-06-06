package com.arda.cloudengineeringproject

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.arda.core_ui.utils.ImageProcessUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {
    companion object {
        lateinit var context: Context
        fun getAppContext(): Context? {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        ImageProcessUtils.initImageProcess(context)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.1)
                    .directory(cacheDir.resolve("image_cache"))
                    .build()
            }
            .logger(DebugLogger())
            .respectCacheHeaders(false)
            .build()
    }

}