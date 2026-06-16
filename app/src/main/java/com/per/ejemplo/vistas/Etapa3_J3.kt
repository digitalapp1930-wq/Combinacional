package com.per.ejemplo.vistas

import android.annotation.SuppressLint
import android.content.Context

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.per.ejemplo.R
import com.per.ejemplo.componentes.BarraInferior
import com.per.ejemplo.componentes.BarraSuperior

import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import com.per.ejemplo.componentes.MarcadorFinal
import com.per.ejemplo.componentes.MarcadorTiempoRegresivo
import kotlinx.coroutines.delay
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel
import com.per.ejemplo.DataStore.CalificacionEtapa
import com.per.ejemplo.viewModel.CalificacionViewModel
import com.per.ejemplo.viewModel.CalificacionViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Etapa3_Juego3(context: Context, navController: NavController, darkMode: MutableState<Boolean>){
    val store = remember { CalificacionEtapa(context) }
    val viewModel: CalificacionViewModel = viewModel(factory = CalificacionViewModelFactory(store))
    // Cargar la calificación al entrar en la pantalla de la etapa 3
    LaunchedEffect("etapa3") { viewModel.cargarCalificacion("etapa3") }
    // Observa el estado
    val nota by viewModel.notaEtapa3.collectAsState()

    Scaffold(
        topBar = { BarraSuperior(navController,puntos="$nota", sector="calificación etapa 3") },
        bottomBar = { BarraInferior (darkMode,navController) }
    ) {innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + 16.dp, // deja espacio arriba
                    bottom = innerPadding.calculateBottomPadding() + 5.dp, // deja espacio para la barra inferior
                    start = 16.dp,
                    end = 16.dp
                )
        ) {

            RelacionaConceptosE3(context,navController)
        }

    }
}

// Nueva clase que soporta texto o imagen
data class Opcion(val id: String, val texto: String? = null, val imagen: Int? = null)

@Composable
fun RelacionaConceptosE3(context: Context, navController: NavController) {

    // Claves
    val clave1 = listOf(
        Opcion("1", texto = "and"), Opcion("2", texto = "or"),
        Opcion("3", imagen = R.drawable.tbl_nand), Opcion("4", imagen = R.drawable.tbl_not))
    // Valores
    val valor1 = listOf(Opcion("1", imagen = R.drawable.ic_and), Opcion("2", imagen = R.drawable.tbl_or),
        Opcion("3", texto = "nand"), Opcion("4", imagen = R.drawable.ic_not))


    val clave2 = listOf(
        Opcion("5", texto = "and"),
        Opcion("6", texto = "or"),
        Opcion("7", imagen = R.drawable.tbl_nand),
        Opcion("8", imagen = R.drawable.tbl_not)
    )

    val valor2 = listOf(
        Opcion("5", imagen = R.drawable.ic_and),
        Opcion("6", imagen = R.drawable.tbl_or),
        Opcion("7", texto = "nand"),
        Opcion("8", imagen = R.drawable.ic_not)
    )

    val clave3 = listOf(
        Opcion("9", texto = "nor"),
        Opcion("10", texto = "or"),
        Opcion("11", imagen = R.drawable.tbl_nand),
        Opcion("12", imagen = R.drawable.tbl_not)
    )

    val valor3 = listOf(
        Opcion("9", imagen = R.drawable.ic_nor),
        Opcion("10", imagen = R.drawable.tbl_or),
        Opcion("11", texto = "nand"),
        Opcion("12", imagen = R.drawable.ic_not)
    )

    val clave4 = listOf(
        Opcion("13", texto = "and"),
        Opcion("14", texto = "or"),
        Opcion("15", imagen = R.drawable.tbl_nand),
        Opcion("16", imagen = R.drawable.tbl_not)
    )

    val valor4 = listOf(
        Opcion("13", imagen = R.drawable.ic_and),
        Opcion("14", imagen = R.drawable.tbl_or),
        Opcion("15", texto = "nand"),
        Opcion("16", imagen = R.drawable.ic_not)
    )

    val clave5 = listOf(
        Opcion("17", texto = "and"),
        Opcion("18", texto = "or"),
        Opcion("19", imagen = R.drawable.tbl_nand),
        Opcion("20", imagen = R.drawable.tbl_not)
    )

    val valor5 = listOf(
        Opcion("17", imagen = R.drawable.ic_and),
        Opcion("18", imagen = R.drawable.tbl_or),
        Opcion("19", texto = "nand"),
        Opcion("20", imagen = R.drawable.ic_not)
    )



    val clavesTotales = listOf(clave1, clave2,clave3,clave4,clave5)
    val valoresTotales = listOf(valor1, valor2,valor3,valor4,valor5)

    var nivelActual by remember { mutableStateOf(0) }
    var aciertos by remember { mutableStateOf(0) }
    var calificacion by remember { mutableStateOf(0) }
    var tiempo by remember { mutableStateOf(45) }

    if (nivelActual >= clavesTotales.size || tiempo == 0) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            MarcadorFinal(aciertos, calificacion, context, navController, "etapa3")
        }
        return
    }

    val claves = remember(nivelActual) {
        mutableStateListOf(*clavesTotales[nivelActual].shuffled().toTypedArray())
    }
    val valores = remember(nivelActual) {
        mutableStateListOf(*valoresTotales[nivelActual].shuffled().toTypedArray())
    }

    var seleccionClave by remember { mutableStateOf<Opcion?>(null) }
    var seleccionValor by remember { mutableStateOf<Opcion?>(null) }

    if (seleccionClave != null && seleccionValor != null) {
        if (seleccionClave!!.id == seleccionValor!!.id) {
            claves.remove(seleccionClave)
            valores.remove(seleccionValor)
            aciertos++
        }
        seleccionClave = null
        seleccionValor = null

        calificacion = aciertos * 50
    }

    LaunchedEffect(nivelActual) {
        while (nivelActual < clavesTotales.size) {
            delay(1000L)
            tiempo--
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MarcadorTiempoRegresivo(tiempo, 45f)
        Spacer(modifier = Modifier.height(16.dp))


        ////AQUI EMPIEZA CAMBIO
        Row(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) { // COLUMNA DE CLAVES
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(claves) { opcion ->
                    TarjetaOpcion(opcion) {
                        seleccionClave = it }
                } }
            Spacer(modifier = Modifier.width(16.dp))  // SIMILAR PARA COLUMNA DE VALORES ...

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(valores) { opcion ->
                    TarjetaOpcion(opcion) {
                        seleccionValor = it
                    }
                }
            }
        }
        /// AQUI FINALIZA

        if (claves.isEmpty() && valores.isEmpty()) {
            nivelActual++
        }
    }
}

@Composable
fun TarjetaOpcion(opcion: Opcion, onClick: (Opcion) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick(opcion) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF59A))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (opcion.imagen != null) {
                Image(
                    painter = painterResource(id = opcion.imagen),
                    contentDescription = opcion.texto ?: "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(100.dp)
                )
            } else {
                Text(
                    text = opcion.texto ?: "",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


