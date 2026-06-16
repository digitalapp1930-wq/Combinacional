package com.per.ejemplo.vistas

import android.media.MediaPlayer
import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.per.ejemplo.R
import com.per.ejemplo.viewModel.SplashState
import com.per.ejemplo.viewModel.SplashViewModel

@Composable
fun SplashScreen(navController: NavController, splashViewModel: SplashViewModel = viewModel()) {
    val state by splashViewModel.state.collectAsState()
    val context = LocalContext.current
    val videoUri = "android.resource://${context.packageName}/${R.raw.combinacional}"
    // Muestra el video
    AndroidView(
        factory = {
            VideoView(it).apply {
                setVideoURI(Uri.parse(videoUri))
                setOnPreparedListener { mp: MediaPlayer -> mp.start() }
                setOnCompletionListener { mp -> mp.release() }
            }
        },
        modifier = Modifier.fillMaxSize()
    )
    // Navega al terminar el splash
    LaunchedEffect(state) {
        if (state is SplashState.Finished) {
            navController.navigate("Home") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
}
