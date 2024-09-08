package com.coroutines.thisdayinhistory

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ThisDayInHistoryApp : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .logger(DebugLogger())
            .crossfade(true)
            .respectCacheHeaders(false)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(MAX_SIZE_PERCENT)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve(CACHE_DIR))
                    .maxSizeBytes(MAX_BYTE_SIZE)
                    .build()
            }
            .build()
    }

    companion object {
        const val MAX_BYTE_SIZE = 5 * 1024 * 1024L
        const val MAX_SIZE_PERCENT = 0.25
        const val CACHE_DIR = "image_cache"
    }
}


