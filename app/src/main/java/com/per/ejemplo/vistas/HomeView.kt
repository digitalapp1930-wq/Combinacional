package com.per.ejemplo.vistas
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.per.ejemplo.componentes.AvancePorcentaje
import com.per.ejemplo.componentes.BarraInferior
import com.per.ejemplo.componentes.BarraProgreso
import com.per.ejemplo.componentes.BarraSuperior
import com.per.ejemplo.componentes.BotonEtapa
import com.per.ejemplo.componentes.Espacio

import android.content.Context
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import com.per.ejemplo.viewModel.CalificacionViewModel
import com.per.ejemplo.viewModel.CalificacionViewModelFactory
import com.per.ejemplo.DataStore.CalificacionEtapa
import androidx.lifecycle.viewmodel.compose.viewModel
import android.util.Log


import com.per.ejemplo.viewModel.VideoViewModel
import com.per.ejemplo.viewModel.VideoViewModelFactory
import com.per.ejemplo.DataStore.VideoEstatus


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Homeview(context: Context, navController: NavController, darkMode: MutableState<Boolean>) {
    val store = remember { CalificacionEtapa(context) }
    val viewModel: CalificacionViewModel = viewModel(factory = CalificacionViewModelFactory(store))
    val etapa1 by viewModel.notaEtapa1.collectAsState(0)
    val etapa2 by viewModel.notaEtapa2.collectAsState(0)
    val etapa3 by viewModel.notaEtapa3.collectAsState(0)
    val etapa4 by viewModel.notaEtapa4.collectAsState(0)
    //Para el avence:
    val videoStore = remember { VideoEstatus(context) }
    val videoViewModel: VideoViewModel = viewModel(factory = VideoViewModelFactory(videoStore))
    LaunchedEffect(Unit) {
        // todos los estados de los videos de las 4 etapas
        videoViewModel.cargarVideo("etapa1_v1")
        videoViewModel.cargarVideo("etapa1_v2")
        videoViewModel.cargarVideo("etapa1_v3")
        videoViewModel.cargarVideo("etapa2_v1")
        videoViewModel.cargarVideo("etapa2_v2")
        videoViewModel.cargarVideo("etapa2_v3")
        videoViewModel.cargarVideo("etapa3_v1")
        videoViewModel.cargarVideo("etapa3_v2")
        videoViewModel.cargarVideo("etapa3_v3")
        videoViewModel.cargarVideo("etapa4_v1")
        videoViewModel.cargarVideo("etapa4_v2")
        videoViewModel.cargarVideo("etapa4_v3")
    }
    val videos by videoViewModel.videosVistos.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.cargarCalificacion("etapa1")
        viewModel.cargarCalificacion("etapa2")
        viewModel.cargarCalificacion("etapa3")
        viewModel.cargarCalificacion("etapa4")
    }
    //  lógica clave
    val notasValidas = listOf(etapa1, etapa2, etapa3, etapa4)
        .filter { it > 0 }

    val promedio = if (notasValidas.isNotEmpty()) {
        notasValidas.sum() / notasValidas.size
    } else { 0 }

    Scaffold(
        topBar = {
            BarraSuperior(
                navController,
                puntos = "$promedio",
                sector = "calificación general"
            )
        },
        bottomBar = { BarraInferior(darkMode, navController) }
    ) {
        ContenidoHomeView(navController, darkMode = darkMode, videos = videos,
            etapa1 = etapa1, etapa2 = etapa2, etapa3 = etapa3, etapa4 = etapa4)
    }
}


@Composable
fun ContenidoHomeView(navController: NavController,darkMode: MutableState<Boolean>,
                      videos: Map<String, Boolean>,etapa1: Int, etapa2: Int, etapa3: Int, etapa4: Int) {

    val avanceEtapa1 = calcularAvance(videos, listOf("etapa1_v1", "etapa1_v2", "etapa1_v3"), etapa1)
    val avanceEtapa2 = calcularAvance(videos, listOf("etapa2_v1", "etapa2_v2", "etapa2_v3"), etapa2)
    val avanceEtapa3 = calcularAvance(videos, listOf("etapa3_v1", "etapa3_v2", "etapa3_v3"), etapa3)
    val avanceEtapa4 = calcularAvance(videos, listOf("etapa4_v1", "etapa4_v2", "etapa4_v3"), etapa4)

    Column(
        modifier = Modifier.fillMaxSize(), // Hace que la columna ocupe toda la pantalla
        verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos horizontalmente

    ) {
        // Aquí puedes agregar tus elementos dentro de la columna, como un botón o texto.


        BotonEtapa(nomEtapa="   Etapa 1: Conceptos digitales"){
            navController.navigate("Etapa1")
        }
        Espacio(5.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically, // Alinea los elementos verticalmente
        )
        {
            AvancePorcentaje("Avance  ")
            BarraProgreso(avanceEtapa1, 250.dp)
            AvancePorcentaje("${(avanceEtapa1 * 100).toInt()}%")
        }

        Espacio(0.5.dp)
        Row(
            modifier = Modifier
                .width(350.dp),  // Ancho específico
            horizontalArrangement = Arrangement.End // Alinear a la derecha
        )
        {
            AvancePorcentaje("Calificación: ")
            AvancePorcentaje(if (etapa1 == 0) "n/a" else etapa1.toString())
        }
        Espacio(30.dp)

        BotonEtapa(nomEtapa="  Etapa 2: Sistemas de numeración y códigos"){
               navController.navigate("Etapa2")
        }
        Espacio(5.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically, // Alinea los elementos verticalmente
        )
        {
            AvancePorcentaje("Avance  ")
            BarraProgreso(avanceEtapa2, 250.dp)
            AvancePorcentaje("${(avanceEtapa2 * 100).toInt()}%")
        }
        Espacio(0.5.dp)
        Row(
            modifier = Modifier
                .width(350.dp),  // Ancho específico
            horizontalArrangement = Arrangement.End // Alinear a la derecha
        )
        {
            AvancePorcentaje("Calificación: ")
            AvancePorcentaje(if (etapa2 == 0) "n/a" else etapa2.toString())
        }
        Espacio(30.dp)

        BotonEtapa(nomEtapa="   Etapa 3: Puertas lógicas"){
            navController.navigate("Etapa3")
        }
        Espacio(5.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically, // Alinea los elementos verticalmente
        )
        {
            AvancePorcentaje("Avance  ")
            BarraProgreso(avanceEtapa3, 250.dp)
            AvancePorcentaje("${(avanceEtapa3 * 100).toInt()}%")
        }

        Espacio(0.5.dp)
        Row(
            modifier = Modifier
                .width(350.dp),  // Ancho específico
            horizontalArrangement = Arrangement.End // Alinear a la derecha
        )
        {
            AvancePorcentaje("Calificación: ")
            AvancePorcentaje(if (etapa3 == 0) "n/a" else etapa3.toString())
        }
        Espacio(30.dp)


        BotonEtapa(nomEtapa="   Etapa 4: Álgebra de Boole \n " +
                            "    y simplificación lógica     "){
            navController.navigate("Etapa4")
        }
        Espacio(5.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically, // Alinea los elementos verticalmente
        )
        {
            AvancePorcentaje("Avance  ")
            BarraProgreso(avanceEtapa4, 250.dp)
            AvancePorcentaje("${(avanceEtapa4 * 100).toInt()}%")
        }

        Espacio(0.5.dp)
        Row(
            modifier = Modifier
                .width(350.dp),  // Ancho específico
            horizontalArrangement = Arrangement.End // Alinear a la derecha
        )
        {
            AvancePorcentaje("Calificación: ")
            AvancePorcentaje(if (etapa4 == 0) "n/a" else etapa4.toString())
        }

    }

}

fun calcularAvance(
    videos: Map<String, Boolean>,
    nombresVideos: List<String>,
    calificacion: Int
): Float {

    val vistos = nombresVideos.count { videos[it] == true }
    val avanceVideos = vistos * 0.25f
    val avanceCalificacion = if (calificacion > 0) 0.25f else 0f

    return (avanceVideos + avanceCalificacion).coerceAtMost(1f)
}


