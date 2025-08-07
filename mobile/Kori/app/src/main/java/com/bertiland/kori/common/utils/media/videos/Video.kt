package api.ttt.rahisi.template1.utils.media.videos

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.delay
import java.io.File

import android.media.MediaMetadataRetriever
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size

import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.layout.ContentScale

import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.pager.PagerState
import api.ttt.rahisi.template1.utils.media.videos.TVideoPlayer.getMediaSource
import api.ttt.rahisi.template1.utils.media.videos.TVideoPlayer.getPreparedPlayer

import com.bertiland.kori.R


/**---------------------------------------------------------------------------------*/

@Composable
fun AsyncVideoPreview(
    videoPath: String,
    modifier: Modifier = Modifier.fillMaxSize(),
    placeholder: Int = R.drawable.placeholder, // Image de placeholder pendant le chargement
    errorImage: Int = R.drawable.error, // Image à afficher en cas d'erreur
) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Charger la preview de la vidéo
    LaunchedEffect(videoPath) {
        bitmap = getVideoFrame(context, videoPath)
        isLoading = false
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when {
            isLoading -> {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(placeholder)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            bitmap != null -> {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                Icon(
                    imageVector = Icons.Outlined.PlayCircleOutline,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.Center)
                )
            }
            else -> {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(errorImage) // Image d'erreur
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Ajuste l'image au conteneur
                )
            }
        }
    }
}

suspend fun getVideoFrame(context: Context, videoPath: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val retriever = MediaMetadataRetriever()
            if (videoPath.startsWith("http")) {
                retriever.setDataSource(videoPath, HashMap())
            } else {
                val file = File(videoPath)
                retriever.setDataSource(file.absolutePath)
            }
            retriever.getFrameAtTime(1_000_000) // Capture une frame à 1 seconde
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

@Composable
fun VideoPreview(videoPath: String, modifier: Modifier = Modifier) {
    // Obtenez le contexte de l'application
    val context = LocalContext.current

    // Utilisez remember pour mémoriser l'image extraite de la vidéo
    val thumbnail = remember(videoPath) {
        // Créez un MediaMetadataRetriever pour extraire un thumbnail
        val retriever = MediaMetadataRetriever()
        try {
            if (videoPath.startsWith("http://") || videoPath.startsWith("https://")) {
                // Pour une URL, utilisez setDataSource avec le contexte et Uri
                retriever.setDataSource(context, Uri.parse(videoPath))
            } else {
                // Pour un fichier local, utilisez directement le chemin
                retriever.setDataSource(videoPath)
            }
            // Obtenez le frame à la position 1 seconde (1000000 microsecondes)
            val bitmap = retriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST)
            bitmap?.asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            retriever.release()
        }
    }

    // Affichez l'image si elle est disponible
    if (thumbnail != null) {
        Image(
            bitmap = thumbnail,
            contentDescription = "Thumbnail de la vidéo",
            modifier = modifier.size(200.dp) // Ajustez la taille selon vos besoins
        )
    }
}

/**---------------------------------------------------------------------------------*/

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun VideoPlayer2(
    mediaLink: String
) {
    val context = LocalContext.current

    // Indicateur pour savoir si la vidéo est prête
    var isVideoReady by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0f) }
    var videoDuration by remember { mutableStateOf(0f) }


    val exoPlayer = remember { getPreparedPlayer(context, mediaLink) }


    // Listener pour mettre à jour l'état lorsque la vidéo est prête
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    isVideoReady = true
                    videoDuration = exoPlayer.duration.toFloat()
                }
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    // Update la position actuelle périodiquement
    LaunchedEffect(isVideoReady) {
        if (isVideoReady) {
            while (true) {
                currentPosition = exoPlayer.currentPosition.toFloat()
                delay(100)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable { // Toggle play/pause on click
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                } else {
                    exoPlayer.play()
                }
            },
        contentAlignment = Alignment.Center // Center the video vertically
    ) {

        // Utiliser AndroidView pour intégrer un PlayerView dans Compose
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false // Contrôleur désactivé si géré à l'extérieur
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Affichage du SeekBar pour contrôler la lecture vidéo, si nécessaire
        if (isVideoReady) {
            Slider(
                value = currentPosition,
                onValueChange = { position ->
                    // Cela ne devrait pas être ici, car la gestion doit se faire à l'extérieur
                    exoPlayer.seekTo(position.toLong())
                },
                valueRange = 0f..videoDuration,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.Gray,
                    inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 30.dp)
            )
        }
    }
}

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun VideoPlayer(
    mediaLink: String,
    pagerState: PagerState,
    pageIndex: Int
) {
    val context = LocalContext.current
    val exoPlayer = TVideoPlayer.getPlayer(context, mediaLink)///remember { ExoPlayer.Builder(context).build() }
    var isVideoReady by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0f) }
    var videoDuration by remember { mutableStateOf(0f) }

    var isPageInitialized by remember { mutableStateOf(false) }

    // Obtenez le MediaSource via la fonction getMediaSource
    val mediaSource = remember(mediaLink) {
        getMediaSource(context, mediaLink)
    }

    // Gestion du cycle de vie pour pause/reprise automatique
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    //TVideoPlayer.pause()
                } // Pause on leaving the screen
                Lifecycle.Event.ON_RESUME -> {
                    ///TVideoPlayer.play(exoPlayer)
                    /**if (pagerState.currentPage == pageIndex) exoPlayer.play()
                    if (pagerState.currentPage == pageIndex && isPageInitialized) {
                        exoPlayer.play()
                    }*/
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            /**exoPlayer.release()*/
        }
    }

    // Gestion de la lecture en fonction de la page active
    /*LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == pageIndex) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }*/

    // Set MediaSource to ExoPlayer
    /*LaunchedEffect(mediaSource) {
        exoPlayer.setMediaSource(mediaSource!!)
        exoPlayer.prepare()
        exoPlayer.play()
        exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
    }*/
    LaunchedEffect(mediaSource, pagerState.currentPage) {
        exoPlayer.setMediaSource(mediaSource!!)
        exoPlayer.prepare()
        TVideoPlayer.play(exoPlayer)
        exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
        /*if (pagerState.currentPage == pageIndex && !isPageInitialized) {
            exoPlayer.setMediaSource(mediaSource!!)
            exoPlayer.prepare()
            TVideoPlayer.play(exoPlayer)
            exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
            isPageInitialized = true // Indique que la page a été initialisée
        } else if (pagerState.currentPage != pageIndex && isPageInitialized) {
            TVideoPlayer.pause()
            isPageInitialized = false // Réinitialise l’état si on quitte la page
        }*/
    }

    // Update the current position periodically
    LaunchedEffect(isVideoReady) {
        if (isVideoReady) {
            while (true) {
                currentPosition = exoPlayer.currentPosition.toFloat()
                delay(100)
            }
        }
    }

    // Listener to update state when video is ready
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    isVideoReady = true
                    videoDuration = exoPlayer.duration.toFloat()
                }
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            /**exoPlayer.release()
            TVideoPlayer.releasePlayer()*/
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable { // Toggle play/pause on click
                if (exoPlayer.isPlaying) {
                    TVideoPlayer.pause()
                    /*
                    exoPlayer.pause()*/
                } else {
                    TVideoPlayer.play(exoPlayer)
                    /*
                    exoPlayer.play()*/
                }
            },
        contentAlignment = Alignment.Center // Center the video vertically
    ) {

        // Use AndroidView to embed an Android View (PlayerView) into Compose
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        // SeekBar for controlling the video playback
        if (isVideoReady) {
            Slider(
                value = currentPosition,
                onValueChange = { position ->
                    exoPlayer.seekTo(position.toLong())
                },
                valueRange = 0f..videoDuration,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.Red,
                    inactiveTrackColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(0.dp)
            )
        }
    }
}

/**---------------------------------------------------------------------------------*/