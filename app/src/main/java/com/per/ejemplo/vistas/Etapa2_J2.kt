package com.per.ejemplo.vistas

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.paint
import androidx.navigation.NavController
import com.per.ejemplo.componentes.BarraInferior
import com.per.ejemplo.componentes.BarraSuperior
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.per.ejemplo.DataStore.CalificacionEtapa
import com.per.ejemplo.R
import com.per.ejemplo.componentes.MarcadorFinal
import com.per.ejemplo.componentes.MarcadorTiempoRegresivo
import com.per.ejemplo.viewModel.CalificacionViewModel
import com.per.ejemplo.viewModel.CalificacionViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Etapa2_Juego2(context: Context, navController: NavController, darkMode: MutableState<Boolean>){
    val store = remember { CalificacionEtapa(context) }
    val viewModel: CalificacionViewModel = viewModel(factory = CalificacionViewModelFactory(store))
    // Cargar la calificación al entrar en la pantalla de la etapa 2
    LaunchedEffect("etapa2") {
        viewModel.cargarCalificacion("etapa2")
    }
    // Observa el estado de la nota de la etapa
    val nota by viewModel.notaEtapa2.collectAsState()

    Scaffold(
        topBar = { BarraSuperior(navController,puntos="$nota", sector="calificación etapa 2") },
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
            JuegoConversiones(context,navController)
        }

    }
}

var sistemas = listOf("binario", "hexadecimal", "octal", "bcd", "ascii")


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuegoConversiones(context: Context,navController: NavController) {



        // Estados principales del juego
        var numero by remember { mutableStateOf((1..255).random()) }
        var sistema by remember { mutableStateOf(generarSistemaAleatorio(numero)) }
        var respuestaUsuario by remember { mutableStateOf("") }
        var mensaje by remember { mutableStateOf("") }

        var preguntasRestantes by remember { mutableStateOf(5) }
        var puntos by remember { mutableStateOf(0) }
        var juegoTerminado by remember { mutableStateOf(false) }

        // Cronómetro → 150 segundos (2 min 30 s)
        var tiempoRestante by remember { mutableStateOf(150) }

        // Contador regresivo
        LaunchedEffect(tiempoRestante, juegoTerminado) {
            if (!juegoTerminado && tiempoRestante > 0) {
                delay(1000)
                tiempoRestante--
            }
            if (tiempoRestante == 0) {
                juegoTerminado = true
            }
        }

        // Fin del juego
        if (juegoTerminado) {

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                MarcadorFinal(puntos,puntos*200,context,navController,"etapa2")
            }

            return
        }


        // UI principal centrada
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            // reloj del juego
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                MarcadorTiempoRegresivo(tiempoRestante,150f)
            }

            Spacer(modifier = Modifier.height(60.dp))


            // Imagen de poustic con ejercicio

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .paint(
                        painterResource(id = R.drawable.libreta6),
                        contentScale = ContentScale.Crop
                    )
                    .padding(16.dp),
                    contentAlignment = Alignment.Center // Centra el contenido dentro de la Box
            ) {

                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Pregunta ${6 - preguntasRestantes} de 5",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF805315),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Convierte el número:",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF805315),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "$numero → $sistema",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color(0xFF805315),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }


            Spacer(modifier = Modifier.height(80.dp))

            //Tu respuesta combo de texto
            Text(
                text = "Tu respuesta:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            //TextField del juego
            OutlinedTextField(
                value = respuestaUsuario,
                onValueChange = { respuestaUsuario = it },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    //  Color del texto que escribe el usuario
                    focusedTextColor = Color(0xFF805315),
                    unfocusedTextColor = Color(0xFF805315),

                    //  Color del fondo
                    focusedContainerColor = Color(0xFFFDF59A),
                    unfocusedContainerColor = Color(0xFFFDF59A),

                    //  Cursor
                    cursorColor = Color(0xFF805315),


                    //  Indicador (el borde inferior)
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,

                    //  Placeholder (texto dentro cuando está vacío)
                    focusedPlaceholderColor = Color.DarkGray,
                    unfocusedPlaceholderColor = Color.DarkGray,
                )
            )


            Spacer(modifier = Modifier.height(10.dp))


            //Mensajes para el estudiante Respuesta botón

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth() //
                        .padding(16.dp)
                        .height(80.dp) // O el alto que quieras
                        .background(
                            Color(0xFFB4E5A2),
                            shape = RoundedCornerShape(16.dp)
                        )// Color de fondo, puedes usar cualquier color
                        .clickable {

                            val correcta = convertir(numero, sistema)

                            if (respuestaUsuario.trim().equals(correcta, ignoreCase = true)) {
                                puntos++
                                mensaje = "✔ ¡Correcto!"
                            } else {
                                mensaje = "✘ Incorrecto. $numero en $sistema es: $correcta"
                            }

                            preguntasRestantes--

                            if (preguntasRestantes == 0) {
                                juegoTerminado = true
                            } else {
                                numero = (1..128).random()
                                sistema = generarSistemaAleatorio(numero)
                                respuestaUsuario = ""
                            }
                        }
                        .padding(16.dp),
                    contentAlignment = Alignment.Center // Centra el contenido dentro de la Box
                ) {
                    Text(
                        text = "Comprobar respuesta",
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }


            Spacer(modifier = Modifier.height(10.dp))

            //Espacio para los mensajes
            if (mensaje.isNotEmpty()) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth() // O el tamaño que necesites
                            .padding(16.dp)
                            .height(160.dp) // O el alto que quieras
                            .background(
                                Color(0x80B4E5A2),
                                shape = RoundedCornerShape(16.dp)
                            ), // Color de fondo, puedes usar cualquier color
                        contentAlignment = Alignment.Center // Centra el contenido dentro de la Box
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = mensaje,
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "¡Sigue Adelante!",
                                color = Color.Black,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                }

            }//fin de if

        }
    }}





//Sistema o código aleatorio
fun generarSistemaAleatorio(num: Int): String {
    when (num) {
        in 0..32 -> { sistemas = listOf("binario", "hexadecimal", "octal", "bcd") }
        in 127..255 -> { sistemas = listOf("binario", "hexadecimal", "octal", "bcd") }
        else -> { listOf("binario", "hexadecimal", "octal", "bcd", "ascii") } }
    return sistemas.random() }

//función encargada de convertir el número decimal enviado al sistema o código requerido
fun convertir(num: Int, sistema: String): String {
    return when (sistema) {
        "binario"     -> Integer.toBinaryString(num)
        "hexadecimal" -> Integer.toHexString(num)
        "octal"       -> Integer.toOctalString(num)
        "bcd"         -> convertirABCD(num)
        "ascii"       -> convertirAAscii(num)
        else          -> "" } }
//Arreglo para código BCD
fun convertirABCD(num: Int): String {
    return num.toString().map {
        Integer.toBinaryString(it.digitToInt()).padStart(4, '0')
    }.joinToString(" ") }
//Condiciones para ASCII
fun convertirAAscii(num: Int): String {
    return if (num in 32..126) num.toChar().toString() else "N/A" }

