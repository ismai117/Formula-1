package ui.sharedComponents

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem


@OptIn(ExperimentalCoilApi::class)
@Composable
fun setSingletonImageLoader(disableDiskCache: Boolean = false){
    setSingletonImageLoaderFactory { context ->
        if (disableDiskCache) context.asyncImageLoader() else
            context.asyncImageLoader().enableDiskCache()
    }
}

fun PlatformContext.asyncImageLoader() =
    ImageLoader
        .Builder(this)
        .components { add(KtorNetworkFetcherFactory()) }
        .crossfade(true)
        .networkCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(this, 0.25)
                .strongReferencesEnabled(true)
                .build()
        }
        .logger(DebugLogger())
        .build()

/**
 * Enable disk cache for the [ImageLoader].
 */
fun ImageLoader.enableDiskCache() = this.newBuilder()
    .diskCache {
        DiskCache.Builder()
            .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
            .build()
    }.build()


@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    url: String,
    description: String
){
    AsyncImage(
        model = url,
        contentDescription = description,
        contentScale = ContentScale.Fit,
        modifier = modifier
    )
}