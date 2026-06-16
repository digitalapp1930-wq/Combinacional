package com.per.ejemplo.vistas

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.media.browse.MediaBrowser
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.per.ejemplo.viewModel.VideoViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.per.ejemplo.R
import android.net.Uri
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.navigation.NavController
import com.per.ejemplo.DataStore.VideoEstatus
import com.per.ejemplo.componentes.BarraInferior
import com.per.ejemplo.componentes.BarraInferiorVideos
import com.per.ejemplo.componentes.BarraSuperior
import com.per.ejemplo.componentes.BotonFlotante
import com.per.ejemplo.viewModel.VideoViewModelFactory



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PantallaVideo(context: Context, navController: NavController, videoName: String) {

    Scaffold(bottomBar = { BarraInferiorVideos(navController) }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            PantallaVideoGeneral(context, navController, videoName) } } }
@Composable
fun PantallaVideoGeneral(context: Context, navController: NavController, videoName: String) {
    // Instancia tu DataStore
    val store = remember { VideoEstatus(context) }
    // CREA el viewmodel correctamente
    val viewModel: VideoViewModel = viewModel(
        factory = VideoViewModelFactory(store)
    )
    // Cargar estado al iniciar
    LaunchedEffect(Unit) {
        viewModel.cargarVideo(videoName)
    }
    // Observa si ya fue visto
    val videos by viewModel.videosVistos.collectAsState()
    val videoYaVisto = videos[videoName] == true
    VideoPlayer(
        context = context,
        videoName = videoName,
        onVideoFinished = {
            viewModel.marcarVideoVisto(videoName)
        }
    )
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(context: Context, videoName: String, onVideoFinished: () -> Unit) {

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Convertir nombre a ID dinámico
    val videoResId = context.resources.getIdentifier(
        videoName,  // ejemplo: "etapa1_v1"
        "raw",
        context.packageName
    )

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val uri = Uri.parse("android.resource://${context.packageName}/$videoResId")
            val mediaItem = MediaItem.fromUri(uri)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }



    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            PlayerView(ctx).apply {
                useController = true
                player = exoPlayer

                //  Modo correcto para videos 16:9 en cualquier orientación RESIZE_MODE_FIT
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT.inc()

                resizeMode = if (isLandscape)
                    AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT.inc()  // Horizontal, llena esquinas sin sacrificar la altura del video
                else
                    AspectRatioFrameLayout.RESIZE_MODE_FIT   // Vertical  no recorta

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
    )

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    onVideoFinished()
                }
            }
        }

        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }
}





