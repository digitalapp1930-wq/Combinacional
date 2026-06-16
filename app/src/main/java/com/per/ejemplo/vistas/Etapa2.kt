package com.per.ejemplo.vistas

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.per.ejemplo.DataStore.CalificacionEtapa
import com.per.ejemplo.DataStore.VideoEstatus
import com.per.ejemplo.componentes.AvancePorcentaje
import com.per.ejemplo.componentes.BarraInferior
import com.per.ejemplo.componentes.BarraProgreso
import com.per.ejemplo.componentes.BarraSuperior
import com.per.ejemplo.componentes.BotonClase
import com.per.ejemplo.componentes.BotonJuego
import com.per.ejemplo.componentes.Espacio
import com.per.ejemplo.componentes.MarcadorClase
import com.per.ejemplo.viewModel.CalificacionViewModel
import com.per.ejemplo.viewModel.CalificacionViewModelFactory
import com.per.ejemplo.viewModel.VideoViewModel
import com.per.ejemplo.viewModel.VideoViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeEtapa2(context: Context, navController: NavController, darkMode: MutableState<Boolean>){
    val store = remember { CalificacionEtapa(context) }
    val viewModel: CalificacionViewModel = viewModel(factory = CalificacionViewModelFactory(store))
    //Para cargar y actualizar la información de la vista de los videos
    val videoViewModel: VideoViewModel = viewModel(factory = VideoViewModelFactory(VideoEstatus(context)))
    LaunchedEffect("videos_etapa2") {
        videoViewModel.cargarVideo("etapa2_v1")
        videoViewModel.cargarVideo("etapa2_v2")
        videoViewModel.cargarVideo("etapa2_v3")
    }
    //Observa el estado de los videos
    val videos by videoViewModel.videosVistos.collectAsState()
    // Cargar la calificación al entrar en la pantalla de la etapa 2
    LaunchedEffect("etapa2") { viewModel.cargarCalificacion("etapa2") }
    // Observa el estado de la nota de la etapa
    val nota by viewModel.notaEtapa2.collectAsState()

    Scaffold(

        topBar = { BarraSuperior(navController,puntos="$nota", sector="calificación etapa 2") },
        bottomBar = { BarraInferior (darkMode,navController) }
    ) {
        ContenidoEtapa2(navController=navController,darkMode=darkMode, nota,videos = videos)
    }
}


@Composable
fun ContenidoEtapa2(navController: NavController, darkMode: MutableState<Boolean>, nota:Int,videos: Map<String, Boolean>) {
    Column(
        modifier = Modifier.fillMaxSize(), // Hace que la columna ocupe toda la pantalla
        verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos horizontalmente
    ) {
        //Redireccionar a la pantalla para el despliegue de videos, se manda identificador para primer video de la etapa 2
        BotonClase(NomClase="   Conversión binaria a decimal   \n " +
                            "          y decimal a binario "){
            navController.navigate("pantallaVideo/etapa2_v1") }

        Espacio(5.dp)

        Row(
            modifier = Modifier
                .width(300.dp),  // Ancho específico
            horizontalArrangement = Arrangement.End // Alinear a la derecha
        )
        {
            MarcadorClase(videos["etapa2_v1"] == true)
        }


        Espacio(30.dp)
        BotonClase (NomClase="     Sistemas de numeración      \n " +
                             "        hexadecimal y octal   "){
            navController.navigate("pantallaVideo/etapa2_v2")
        }
        Espacio(5.dp)
        Row(
            modifier = Modifier
                .width(300.dp),  // Ancho específico
            horizontalArrangement = Arrangement.End // Alinear a la derecha
        )
        {
            MarcadorClase(videos["etapa2_v2"] == true)
        }
        Espacio(30.dp)


        BotonClase (NomClase="     Código BCD y ASCII     "){
            navController.navigate("pantallaVideo/etapa2_v3")
        }
        Espacio(5.dp)
        Row(
            modifier = Modifier
                .width(300.dp),  // Ancho específico
            horizontalArrangement = Arrangement.End // Alinear a la derecha
        )
        {
            MarcadorClase(videos["etapa2_v3"] == true)
        }
        Espacio(30.dp)


        BotonJuego(NomJuego = "   Conversión entre sistemas   "){
            navController.navigate("GameEtapa2")
        }
        Espacio(5.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically, // Alinea los elementos verticalmente
        )
        {
            var progreso by remember { mutableStateOf(0f) }
            progreso=nota.toFloat()/1000
            AvancePorcentaje("Calificación ")
            BarraProgreso(progreso, 200.dp)
            AvancePorcentaje("$nota")
        }

    }

}

