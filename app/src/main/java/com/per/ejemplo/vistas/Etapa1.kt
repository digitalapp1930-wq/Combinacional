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
import com.per.ejemplo.DataStore.VideoEstatus

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeEtapa1(context: Context, navController: NavController, darkMode: MutableState<Boolean>){
    val store = remember { CalificacionEtapa(context) }
    val viewModel: CalificacionViewModel = viewModel(factory = CalificacionViewModelFactory(store))
    val videoViewModel: VideoViewModel = viewModel(factory = VideoViewModelFactory(VideoEstatus(context)))

    LaunchedEffect("videos_etapa1") {
        videoViewModel.cargarVideo("etapa1_v1")
        videoViewModel.cargarVideo("etapa1_v2")
        videoViewModel.cargarVideo("etapa1_v3")

    }

    //Observa el estado de los videos
    val videos by videoViewModel.videosVistos.collectAsState()

    // Cargar la calificación al entrar en la pantalla
    LaunchedEffect("etapa1") {
        viewModel.cargarCalificacion("etapa1")
    }

    // Observa el estado
    val nota by viewModel.notaEtapa1.collectAsState()

    Scaffold(

        topBar = {BarraSuperior(navController,puntos="$nota", sector="calificación etapa 1")},
        bottomBar = { BarraInferior (darkMode,navController) }
    ) {
        ContenidoEtapa1(navController=navController,darkMode=darkMode, nota,videos = videos)
    }
}


@Composable
fun ContenidoEtapa1( navController: NavController,darkMode: MutableState<Boolean>, nota:Int,videos: Map<String, Boolean>) {



    Column(
        modifier = Modifier.fillMaxSize(), // Hace que la columna ocupe toda la pantalla
        verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos horizontalmente
    ) {
        // elementos dentro de la columna:

        BotonClase(NomClase="  Magnitudes analógicas y digitales"){
            navController.navigate("pantallaVideo/etapa1_v1")
        }
        Espacio(5.dp)
        Row(
            modifier = Modifier
                .width(300.dp),  // Ancho específico
            horizontalArrangement = Arrangement.End // Alinear a la derecha
        )
        {
            MarcadorClase(videos["etapa1_v1"] == true)
        }


        Espacio(30.dp)
        BotonClase (NomClase="  Niveles lógicos y formas de onda digitales"){
            navController.navigate("pantallaVideo/etapa1_v2")
        }
        Espacio(5.dp)
        Row(
            modifier = Modifier
                .width(300.dp),  // Ancho específico
            horizontalArrangement = Arrangement.End // Alinear a la derecha
        )
        {
            MarcadorClase(videos["etapa1_v2"] == true)
        }
        Espacio(30.dp)


        BotonClase (NomClase="  Circuitos integrados de función fija"){
            navController.navigate("pantallaVideo/etapa1_v3")
        }
        Espacio(5.dp)
        Row(
            modifier = Modifier
                .width(300.dp),  // Ancho específico
            horizontalArrangement = Arrangement.End // Alinear a la derecha
        )
        {
            MarcadorClase(videos["etapa1_v3"] == true)
        }
        Espacio(30.dp)


        BotonJuego(NomJuego = "Asociación de conceptos"){
            navController.navigate("GameEtapa1")
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

