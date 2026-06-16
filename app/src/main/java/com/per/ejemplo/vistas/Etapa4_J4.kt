package com.per.ejemplo.vistas

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.per.ejemplo.DataStore.CalificacionEtapa
import com.per.ejemplo.R
import com.per.ejemplo.componentes.BarraInferior
import com.per.ejemplo.componentes.BarraSuperior
import com.per.ejemplo.componentes.BotonVerde
import com.per.ejemplo.componentes.MarcadorFinal
import com.per.ejemplo.componentes.MarcadorTiempoRegresivo
import com.per.ejemplo.viewModel.CalificacionViewModel
import com.per.ejemplo.viewModel.CalificacionViewModelFactory
import kotlinx.coroutines.delay
import kotlin.random.Random



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Etapa4_Juego4(context: Context, navController: NavController, darkMode: MutableState<Boolean>){

    val store = remember { CalificacionEtapa(context) }
    val viewModel: CalificacionViewModel = viewModel(factory = CalificacionViewModelFactory(store))
    // Cargar la calificación al entrar en la pantalla de la etapa 4
    LaunchedEffect("etapa4") {
        viewModel.cargarCalificacion("etapa4")
    }
    // Observa el estado
    val nota by viewModel.notaEtapa4.collectAsState()

    Scaffold(
        topBar = { BarraSuperior(navController,puntos="$nota", sector="calificación etapa 4") },
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

            JuegoBooleano3Vars(context,navController)
        }

    }
}



// ---------------------- UI (Compose) ----------------------

@Composable
fun JuegoBooleano3Vars(context: Context, navController: NavController) {
    // configuración
    val totalPreguntas = 5
    val totalTiempoSegundos = 300 // 5 minutes

    // estado del juego
    var actualMinterms by remember { mutableStateOf(generaExprecionBooleana()) }
    var originalSOP by remember { mutableStateOf(actualMinterms) }
    var correctSOP by remember { mutableStateOf(minimizarSOP(listaMiniterminos)) }
    var correctPOS by remember { mutableStateOf(generarPOSminima(listaMiniterminos)) }

    var campoRespuesta by remember { mutableStateOf(TextFieldValue("")) }
    var respuestas by remember { mutableStateOf("") }
    var indicePreguntas by remember { mutableStateOf(1) }
    var puntaje by remember { mutableStateOf(0) }
    var finalizaJuego by remember { mutableStateOf(false) }

    var tiempoRestante by remember { mutableStateOf(totalTiempoSegundos) }

    // temporizador regresivo
    LaunchedEffect(tiempoRestante, finalizaJuego) {
        if (!finalizaJuego && tiempoRestante > 0) {
            delay(1000L)
            tiempoRestante -= 1
            if (tiempoRestante <= 0) finalizaJuego = true
        }
    }

    //termina el juego
    if (finalizaJuego) {
        MarcadorFinal(puntaje, puntaje*200, context, navController, "etapa4")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MarcadorTiempoRegresivo(tiempoRestante, 350f)
        Spacer(Modifier.height(60.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .paint(
                    painterResource(id = R.drawable.hoja_j10),
                    contentScale = ContentScale.Crop
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center // Centra el contenido dentro de la Box
        ) {

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Pregunta $indicePreguntas de $totalPreguntas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF805315),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Encuentra la SOP o POS mínima:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF805315),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )


                Text(
                    text = actualMinterms,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF805315),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

            }
        }
        Spacer(Modifier.height(40.dp))

        Text(
            text = "Usa: ' para (NOT), * para (AND), + para (OR).\n" +
                    "Usa () para agrupar términos. Ejemplo: (A*B)+(C').",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF805315),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFDF59A),             // Fondo amarillo suave
                    shape = RoundedCornerShape(8.dp)      // Bordes redondeados
                )
                .padding(12.dp),                           // Padding interno del recuadro
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(20.dp))
        //Tu respuesta combo de texto
        Text(
            text = "Tu respuesta:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        //TextField del juego
        Spacer(Modifier.height(5.dp))
        OutlinedTextField(
            value = campoRespuesta,
            onValueChange = { campoRespuesta = it },
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



        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            //Primer botón, en el que comprobaremos la respuesta

            BotonVerde(
                "Comprobar",
                onClick = {   val userExpr = campoRespuesta.text
                    try {
                        val userRespuesta = userExpr.replace("\\s+".toRegex(), "")
                        val sopCalculada = minimizarSOP(listaMiniterminos).replace("\\s+".toRegex(), "")
                        val posCalculada=generarPOSminima(listaMiniterminos).replace("\\s+".toRegex(), "")
                        if (userRespuesta.contentEquals(sopCalculada) ||userRespuesta.contentEquals(posCalculada) ) {
                            respuestas = "¡Correcto!"
                            puntaje++

                        } else {

                            respuestas = "Incorrecto. Respuesta mínima (SOP): ${minimizarSOP(listaMiniterminos)} \nPOS mínima: ${generarPOSminima(listaMiniterminos)}"
                        }
                    } catch (e: Exception) {
                        respuestas = "Error al evaluar tu expresión: ${e.message}"
                    } },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .padding(4.dp)
            )

            // fin de primer botón

            //Segundo botón, mostrar respuesta


            BotonVerde(
                "Mostrar respuesta",
                onClick = { // Saltar: mostrar respuesta y pasar a la siguiente
                    respuestas = "Respuesta mínima (SOP):${minimizarSOP(listaMiniterminos)} \nPOS mínima: ${generarPOSminima(listaMiniterminos)}" },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .padding(4.dp)
            )
             //fin de segundo botón

            //Tercer botón, siguiente

            BotonVerde(
                "Siguiente",
                onClick = {  // nueva pregunta
                    if (indicePreguntas >= totalPreguntas) {
                        finalizaJuego = true
                    } else {
                        indicePreguntas++
                        actualMinterms = generaExprecionBooleana()
                        originalSOP = actualMinterms
                        correctSOP = minimizarSOP(listaMiniterminos)
                        correctPOS = generarPOSminima(listaMiniterminos)
                        campoRespuesta = TextFieldValue("")
                        respuestas = ""
                    } },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .padding(4.dp)
            )
        }

       /* Spacer(Modifier.height(20.dp))
        Text(respuestas, color = Color.Black)
        Spacer(Modifier.height(20.dp))
        Text("Puntaje: $puntaje", fontSize = 18.sp)*/


        Box(
            modifier = Modifier
                .fillMaxWidth() // O el tamaño que necesites
                .padding(16.dp)
                .height(160.dp) // O el alto que quieras
                .background(Color(0x80B4E5A2),
                    shape = RoundedCornerShape(16.dp)), // Color de fondo, puedes usar cualquier color
            contentAlignment = Alignment.Center // Centra el contenido dentro de la Box
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = respuestas, color = Color.Black, fontSize = 16.sp,fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center)
                Text(text = "Aciertos: $puntaje", color = Color.Black, fontSize = 16.sp,fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center)
            }
        }

    }
}


// tenemos muestra primera variable global que basicamente
// nos ayudara a acceder a los miniterminos a reducir
val listaMiniterminos = mutableListOf<Int>()

// Función para generar expresiones booleanas aleatorias
fun generaExprecionBooleana(): String {
    listaMiniterminos.clear() //  Limpia la lista antes de generar nuevos valores
    val cantidad = Random.nextInt(2, 5) // Cantidad aleatoria de minitérminos (2 a 4)
    while (listaMiniterminos.size < cantidad) { // Generar nuevos minitérminos y guardarlos en la lista global
        listaMiniterminos.add(Random.nextInt(0, 8))} // generamos un número aleatorio 0 incluido 8 excluido
    return listaMiniterminos.joinToString(separator = " + ") { // Se convierten los números a términos
        convertirMintermATermino(it) }
}
// Convertiremos un número de 0 a 7 en un término booleano:
fun convertirMintermATermino(minitermino: Int): String {
    return when (minitermino) {
        0 -> "A'B'C'"
        1 -> "A'B'C"
        2 -> "A'BC'"
        3 -> "A'BC"
        4 -> "AB'C'"
        5 -> "AB'C"
        6 -> "ABC'"
        7 -> "ABC"
        else -> "A'B'C'" }
}


/**
 * Minimiza una función booleana (máximo 3 variables A,B,C)
 * dada una lista de miniterminos.
 * Ejemplo: si la función es A'B'C' + A'BC' + ABC,
 * los miniterminos son [0, 2, 7]
 */
fun minimizarSOP(minterms: List<Int>): String {
    println("miniterminosSOP: $minterms")
    // Si no hay minitérminos, la función es 0
    if (minterms.isEmpty()) {
        return "0"
    }
    // Si están todos los minitérminos, la función es 1
    if (minterms.containsAll(listOf(0,1,2,3,4,5,6,7))) {
        return "1"
    }

    // Agrupaciones para simplificar (mapa de Karnaugh)
    var grupos = mutableListOf<String>()
    var gruposPares = mutableListOf<String>()
    val miniterminosAgrupados = mutableSetOf<Int>()
    val miniterminosParesAgrupados=mutableSetOf<Int>()


    // 1. Grupos de 4
    if (listOf(0, 1, 2, 3).all { it in minterms }) {
        miniterminosAgrupados.addAll(listOf(0, 1, 2, 3))
        grupos.add("(A')") // A NO CAMBIA EN ESTE GRUPO
    }
    if (listOf(4, 5, 6, 7).all { it in minterms }) {
        miniterminosAgrupados.addAll(listOf(4, 5, 6, 7))
        grupos.add("(A)") // A NO CAMBIA EN ESTE GRUPO
    }

    if (listOf(0, 1, 4, 5).all { it in minterms }) {
        miniterminosAgrupados.addAll(listOf(0, 1, 4, 5))
        grupos.add("(B')") // B NO CAMBIA EN ESTE GRUPO
    }
    if (listOf(2, 3, 6, 7).all { it in minterms }) {
        miniterminosAgrupados.addAll(listOf(2, 3, 6, 7))
        grupos.add("(B)")// B NO CAMBIA EN ESTE GRUPO
    }


    if (listOf(0, 2, 4, 6).all { it in minterms }) {
        miniterminosAgrupados.addAll(listOf(0, 2, 4, 6))
        grupos.add("(C')") // C NO CAMBIA
    }
    if (listOf(1, 3, 5, 7).all { it in minterms }) {
        miniterminosAgrupados.addAll(listOf(1, 3, 5, 7))
        grupos.add("(C)") // C NO CAMBIA
    }



    // 2. Grupos de 2 -> pares
    val pares = listOf(
        listOf(0, 1) to "(A'*B')",

        listOf(2, 3) to "(A'*B)",

        listOf(6, 7) to "(A*B)",

        listOf(4, 5) to "(A*B')",

        listOf(0, 2) to "(A'*C')",

        listOf(2, 6) to "(B*C')",

        listOf(4, 6) to "(A*C')",

        listOf(1, 3) to "(A'*C)",

        listOf(3, 7) to "(B*C)",

        listOf(5, 7) to "(A*C)",

        listOf(0, 4) to "(B'*C')",

        listOf(5, 1) to "(B'*C)")

    if (listOf(0,1).all { it in minterms }) {
        miniterminosParesAgrupados.addAll(listOf(0,1))
        gruposPares.add("(A'*B')")
    }
    if (listOf(2,3).all { it in minterms }) {
        miniterminosParesAgrupados.addAll(listOf(2,3))
        gruposPares.add("(A'*B)")
    }
    if (listOf(6,7).all { it in minterms }) {
        miniterminosParesAgrupados.addAll(listOf(6,7))
        gruposPares.add("(A*B)")
    }
    if (listOf(4, 5).all { it in minterms }) {
        miniterminosParesAgrupados.addAll(listOf(4, 5))
        gruposPares.add("(A*B')")
    }
    if (listOf(0, 2).all { it in minterms }) {
        miniterminosParesAgrupados.addAll(listOf(0, 2))
        gruposPares.add("(A'*C')")
    }
    if (listOf(2, 6).all { it in minterms }) {
        miniterminosParesAgrupados.addAll(listOf(2, 6))
        gruposPares.add("(B*C')")
    }
    if (listOf(4, 6).all { it in minterms }) {
        miniterminosParesAgrupados.addAll(listOf(4, 6))
        gruposPares.add("(A*C')")
    }
    if (listOf(1, 3).all { it in minterms }) {
        miniterminosParesAgrupados.addAll(listOf(1, 3))
        gruposPares.add("(A'*C)")
    }
    if (listOf(3, 7).all { it in minterms }) {
        miniterminosParesAgrupados.addAll(listOf(3, 7))
        gruposPares.add("(B*C)")
    }
    if (listOf(5, 7).all { it in minterms }) {
        miniterminosParesAgrupados.addAll(listOf(5, 7))
        gruposPares.add("(A*C)")
    }
    if (listOf(0, 4).all { it in minterms }) {
        miniterminosParesAgrupados.addAll(listOf(0, 4))
        gruposPares.add("(B'*C')")
    }
    if (listOf(5, 1).all { it in minterms }) {
        miniterminosParesAgrupados.addAll(listOf(5, 1))
        gruposPares.add("(B'*C)")
    }

    // Si se formaron grupos de 4, sin que falte incluir algun elemento de la lista de maxitérminos ya es mínima
    if ((grupos.isNotEmpty()) && (miniterminosAgrupados.containsAll(minterms))) {
        return grupos.joinToString("+")
    } else if ((grupos.isNotEmpty()) && (minterms.size > miniterminosAgrupados.size)) {
        val mintermsFaltantes = minterms.toSet() - miniterminosAgrupados.toSet() //Minitérminos que no han sido agrupdos
        // filtramos por pares que pueden estar en relación a todos los maxitérminos de la expresión
        val factoresGenerales = pares.filter { (par, _) ->
            par.all { it in minterms }
        } //Tomamos los pares que faltan
        if (factoresGenerales.isNotEmpty()) {
            val factores = factoresGenerales.filter { (fac, _) ->
                fac.any { it in mintermsFaltantes }
            }.map { it.second }
            //agregamos los pares faltantes a la expresión formada por grupos de 4
            grupos.addAll(factores) }
    } else if (grupos.isEmpty()) {// Si hubo pares simplificados
        if (gruposPares.isNotEmpty()) {
            grupos.addAll(gruposPares) // agregamos los pares localizados
            if(miniterminosParesAgrupados.size <minterms.size){ //validamos si es que falta algún minitérmino
                // si falta se agrega en su forma canónica
                val mintermsDesParFaltantes: List<Int> =
                    (minterms.toSet() - miniterminosParesAgrupados.toSet()).toList()
                grupos.addAll(sopCanonica(mintermsDesParFaltantes)) }
        }else {//  Si no hubo grupos → se usa la sop canónica
            grupos.addAll(sopCanonica(minterms)) } }
    return grupos.joinToString("+")
}


// Función para generar la POS canonica con  3 variables maximo
fun generarPOS(maxiterminosCan: List<Int>): List<String> {

    val listaFinalMaxiterminos = mutableListOf<String>()
    for (n in maxiterminosCan) {
        val convertido = when (n) {
            0 -> "(A+B+C)"
            1 -> "(A+B+C')"
            2 -> "(A+B'+C)"
            3 -> "(A+B'+C')"
            4 -> "(A'+B+C)"
            5 -> "(A'+B+C')"
            6 -> "(A'+B'+C)"
            7 -> "(A'+B'+C')"
            else -> "(A+B+C)"
        }

        listaFinalMaxiterminos.add(convertido)
    }
    return listaFinalMaxiterminos

}

fun sopCanonica(miniterminosCan: List<Int>): List<String> {
    val listaFinalMiniterminos = mutableListOf<String>()
    for (n in miniterminosCan) {
        val convertido = when (n) {
            0 -> "(A'*B'*C')"
            1 -> "(A'*B'*C)"
            2 -> "(A'*B*C')"
            3 -> "(A'*B*C)"
            4 -> "(A*B'*C')"
            5 -> "(A*B'*C)"
            6 -> "(A*B*C')"
            7 -> "(A*B*C)"
            else -> "(A'*B'*C')"
        }

        listaFinalMiniterminos.add(convertido)
    }
    return listaFinalMiniterminos
}


fun generarPOSminima(miniterminos: List<Int>): String {

    val todos = (0..7).toList()

    val maxterminos = todos.filter { it !in miniterminos }

    // Si no hay maxitérminos, la función es 1
    if (maxterminos.isEmpty()) {
        return "1"
    }

    // Si están todos los maxitérminos, la función es 0
    if (maxterminos.size == 8) {
        return "0"
    }

    // Agrupaciones para simplificar (mapa de Karnaugh)
    var grupos = mutableListOf<String>()
    var gruposPares= mutableListOf<String>()
    val maxiterminosAgrupados = mutableSetOf<Int>()
    val maxiterminosParesAgrupados = mutableSetOf<Int>()

    // 1. Grupos de 4

    if (listOf(0, 1, 2, 3).all { it in maxterminos }) {
        maxiterminosAgrupados.addAll(listOf(0, 1, 2, 3))
        grupos.add("(A)") // A NO CAMBIA EN ESTE GRUPO
    }

    if (listOf(4, 5, 6, 7).all { it in maxterminos }) {
        maxiterminosAgrupados.addAll(listOf(4, 5, 6, 7))
        grupos.add("(A')") // A NO CAMBIA EN ESTE GRUPO
    }
    if (listOf(0, 1, 4, 5).all { it in maxterminos }) {
        maxiterminosAgrupados.addAll(listOf(0, 1, 4, 5))
        grupos.add("(B)") // B NO CAMBIA EN ESTE GRUPO
    }
    if (listOf(2, 3, 6, 7).all { it in maxterminos }) {
        maxiterminosAgrupados.addAll(listOf(2, 3, 6, 7))
        grupos.add("(B')")// B NO CAMBIA EN ESTE GRUPO
    }
    if (listOf(0, 2, 4, 6).all { it in maxterminos }) {
        maxiterminosAgrupados.addAll(listOf(0, 2, 4, 6))
        grupos.add("(C)") // C NO CAMBIA
    }
    if (listOf(1, 3, 5, 7).all { it in maxterminos }) {
        maxiterminosAgrupados.addAll(listOf(1, 3, 5, 7))
        grupos.add("(C')") // C NO CAMBIA
    }

    // 2. Grupos de 2 -> pares
    val pares = listOf(
        listOf(0, 1) to "(A+B)",
        listOf(2, 3) to "(A+B')",
        listOf(6, 7) to "(A'+B')",
        listOf(4, 5) to "(A'+B)",

        listOf(0, 2) to "(A+C)",
        listOf(2, 6) to "(B'+C)",
        listOf(4, 6) to "(A'+C)",
        listOf(1, 3) to "(A+C')",

        listOf(3, 7) to "(B'+C')",
        listOf(5, 7) to "(A'+C')",
        listOf(0, 4) to "(B+C)",
        listOf(5, 1) to "(B+C')"
    )

    if (listOf(0,1).all { it in maxterminos }) {
        maxiterminosParesAgrupados.addAll(listOf(0,1))
        gruposPares.add("(A+B)")
    }
    if (listOf(2,3).all { it in maxterminos }) {
        maxiterminosParesAgrupados.addAll(listOf(2,3))
        gruposPares.add("(A+B')")
    }
    if (listOf(6,7).all { it in maxterminos }) {
        maxiterminosParesAgrupados.addAll(listOf(6,7))
        gruposPares.add("(A'+B')")
    }
    if (listOf(4, 5).all { it in maxterminos }) {
        maxiterminosParesAgrupados.addAll(listOf(4, 5))
        gruposPares.add("(A'+B)")
    }

    if (listOf(0, 2).all { it in maxterminos }) {
        maxiterminosParesAgrupados.addAll(listOf(0, 2))
        gruposPares.add("(A+C)")
    }

    if (listOf(2, 6).all { it in maxterminos }) {
        maxiterminosParesAgrupados.addAll(listOf(2, 6))
        gruposPares.add("(B'+C)")
    }
    if (listOf(4, 6).all { it in maxterminos }) {
        maxiterminosParesAgrupados.addAll(listOf(4, 6))
        gruposPares.add("(A'+C)")
    }

    if (listOf(1, 3).all { it in maxterminos }) {
        maxiterminosParesAgrupados.addAll(listOf(1, 3))
        gruposPares.add("(A+C')")
    }

    if (listOf(3, 7).all { it in maxterminos }) {
        maxiterminosParesAgrupados.addAll(listOf(3, 7))
        gruposPares.add("(B'+C')")
    }
    if (listOf(5, 7).all { it in maxterminos }) {
        maxiterminosParesAgrupados.addAll(listOf(5, 7))
        gruposPares.add("(A'+C')")
    }
    if (listOf(0, 4).all { it in maxterminos }) {
        maxiterminosParesAgrupados.addAll(listOf(0, 4))
        gruposPares.add("(B+C)")
    }
    if (listOf(5, 1).all { it in maxterminos }) {
        maxiterminosParesAgrupados.addAll(listOf(5, 1))
        gruposPares.add("(B+C')")
    }

    // Si se formaron grupos de 4, sin que falte incluir algun elemento de la lista de maxiterminos ya es mínima
    if ((grupos.isNotEmpty()) && (maxiterminosAgrupados.containsAll(maxterminos))) {
        return grupos.joinToString("*")

    } else if ((grupos.isNotEmpty()) && (maxterminos.size > maxiterminosAgrupados.size)) {
        val maxiterminosFaltantes = maxterminos.toSet() - maxiterminosAgrupados.toSet()

        // filtramos por pares que pueden estar en relación a todos
        // los maxiterminos de la expreción
        val factoresGenerales = pares.filter { (par, _) ->
            par.all { it in maxterminos }
        }

        //Tomamos los pares que faltan
        if (factoresGenerales.isNotEmpty()) {
            val factores = factoresGenerales.filter { (fac, _) ->
                fac.any { it in maxiterminosFaltantes }
            }.map { it.second }
            grupos.addAll(factores)
        }

    } else if (grupos.isEmpty()) {

        // Si hubo pares simplificados
        if (gruposPares.isNotEmpty()) {
            grupos.addAll(gruposPares) // agregamos los pares localizados
            if(maxiterminosParesAgrupados.size <maxterminos.size){ //validamos si es que falta algún maxitermino
                // si falta se agrega en su forma canónica
                val maxtermDesParFaltantes: List<Int> =
                    (maxterminos.toSet() - maxiterminosParesAgrupados.toSet()).toList()
                grupos.addAll(generarPOS(maxtermDesParFaltantes))
            }
        }else {
            //  Si no hubo grupos → se usa la sop canónica
            grupos.addAll(generarPOS(maxterminos))
        }
    }

    return grupos.joinToString("*")
}
