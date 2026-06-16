package com.per.ejemplo.vistas

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.per.ejemplo.DataStore.CalificacionEtapa
import com.per.ejemplo.componentes.BarraInferior
import com.per.ejemplo.componentes.BarraSuperior
import com.per.ejemplo.componentes.Espacio
import com.per.ejemplo.componentes.MarcadorFinal
import com.per.ejemplo.componentes.MarcadorTiempo
import com.per.ejemplo.viewModel.CalificacionViewModel
import com.per.ejemplo.viewModel.CalificacionViewModelFactory
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Etapa1_Juego1(context: Context,navController: NavController, darkMode: MutableState<Boolean>){
    val store = remember { CalificacionEtapa(context) }
    val viewModel: CalificacionViewModel = viewModel(factory = CalificacionViewModelFactory(store))
    // Cargar la calificación al entrar en la pantalla
    LaunchedEffect("etapa1") {
        viewModel.cargarCalificacion("etapa1")
    }
    // Observa el estado
    val nota by viewModel.notaEtapa1.collectAsState()

    Scaffold(
        topBar = { BarraSuperior(navController,puntos="$nota", sector="calificación etapa 1") },
        bottomBar = { BarraInferior (darkMode,navController) }
    ) { RelacionaConceptosE1(context,navController) }
}


@Composable
fun RelacionaConceptosE1(context: Context,navController: NavController) {
    val clave1 = listOf(
        "1" to "SSI (Small-Scale Integration)\n", "2" to "MSI (Medium-Scale Integration)\n",
        "3" to "ULSI (Ultra Large- Scale Integration)\n", "4" to "ejemplo de magnitud analógica\n"
    )

    val valor1 = listOf(
        "1" to "Hasta 10 puertas equivalentes por chip\n", "2" to "Entre 10 y 100 puertas equivalentes por chip\n",
        "3" to "Más de 100 000 puertas; incluye microprocesadores avanzados\n", "4" to "presión atmosférica\n"
    )

    val clave2 = listOf(
        "5" to "ejemplo de magnitud digital\n", "6" to "Ejemplo de sistema analógico\n",
        "7" to "Ejemplo de sistema digital\n", "8" to "magnitud\n"
    )

    val valor2 = listOf(
        "5" to "puntuación en un examen\n", "6" to "termómetro de mercurio\n",
        "7" to "computadora\n", "8" to "puede medirse y expresarse numéricamente\n"
    )

    val clave3 = listOf(
        "9" to "Sistema electrónico analógico", "10" to "Sistema electrónico digital", "11" to "Muestreo", "12" to "Conversión analógica- digital (A/D)"
    )

    val valor3 = listOf(
        "9" to "Procesa señales continuas", "10" to "Procesa señales binarias (0 y 1)", "11" to "Convertir una señal analógica en valores discretos", "12" to "Convertir señal continua a datos digitales"
    )

    val clave4 = listOf(
        "13" to "Formato binario", "14" to "Forma de onda", "15" to "Fase de una señal", "16" to "Bit"
    )

    val valor4 = listOf(
        "13" to "Representación digital que utiliza", "14" to "Grafica de comportamiento de una señal en el tiempo", "15" to "Posición relativa de una onda respecto a otra en el tiempo.", "16" to "Unidad mínima de información digital"
    )

    val clave5 = listOf(
        "17" to "Nivel lógico alto (HIGH)", "18" to "Nivel lógico bajo (LOW)", "19" to "Lógica positiva", "20" to "Lógica negativa"
    )

    val valor5 = listOf(
        "17" to "Representa el 1 binario en lógica positiva", "18" to "Representa el 0 binario en lógica positiva.", "19" to "1=ALTO, 0=BAJO", "20" to "0=ALTO, 1=BAJO"
    )
    val clave6 = listOf(
        "21" to "Región indeterminada", "22" to "Señal digital", "23" to "Impulso positivo", "24" to "Impulso negativo"
    )

    val valor6 = listOf(
        "21" to "Rango de voltaje donde nivel ALTO o BAJO no es definido", "22" to "Serie de impulsos que representan bits", "23" to "Flanco anterior de subida y posterior de bajada", "24" to "Flanco anterior de bajada y posterior de subida"
    )

    val clave7 = listOf(
        "25" to "Flanco de subida", "26" to "Flanco de bajada", "27" to "Tiempo de subida", "28" to "Tiempo de bajada "
    )

    val valor7 = listOf(
        "25" to "Transición de BAJO a ALTO", "26" to "Transición de ALTO a BAJO", "27" to "Intervalo del 10% al 90% de amplitud", "28" to "Intervalo del 90% al 10% de amplitud"
    )

    val clave8 = listOf(
        "29" to "Ancho de impulso", "30" to "Forma de onda periódica", "31" to "Periodo (T)", "32" to "Frecuencia (f)"
    )

    val valor8 = listOf(
        "29" to "Duración del impulso medida al 50%", "30" to "Señal que se repite cada período (T)", "31" to "Tiempo que tarda en repetirse una señal", "32" to "Numero de repeticiones por segundo (Hz)"
    )

    val clave9 = listOf(
        "33" to "Señal de reloj", "34" to "Envío de bits uno a uno por una sola línea", "35" to "Circuito integrado de función fija", "36" to "Circuito integrado programable"
    )

    val valor9 = listOf(
        "33" to "Señal periódica que sincroniza la información", "34" to "Transferencia en serie", "35" to "Chip con funciones lógicas predeterminadas", "36" to "Chip cuyas funciones pueden configurarse"
    )
    val clave10 = listOf(
        "37" to "Montaje de inserción (Through-Hole)", "38" to "Montaje superficial (SMT)", "39" to "PCB (Printed Circuit Board)", "40" to "Pin 1"
    )

    val valor10 = listOf(
        "37" to "Requiere perforaciones en la placa de PBC", "38" to "Se instala sobre la superficie del PBC", "39" to "Placa donde se monta y conectan componentes electrónicos", "40" to "Primer terminal identificado por la muesca"
    )

    val clavesTotales = listOf(clave1, clave2, clave3,clave4,clave5,clave6,clave7,clave8)
    val valoresTotales = listOf(valor1, valor2, valor3,valor4,valor5,valor6,valor7,valor8)

    var nivelActual by remember { mutableStateOf(0) } //Variable para supervisar despliegue de Cards
    var aciertos by remember { mutableStateOf(0) } // Variable para almacenar los aciertos del jugador
    var calificacion by remember { mutableStateOf(0) } // Variable para almacenar calificación del jugador
    var tiempo by remember { mutableStateOf(90) }//Creamos una variable para el reloj del juego

    if (nivelActual >= clavesTotales.size || tiempo == 0) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            MarcadorFinal(aciertos,calificacion,context,navController,"etapa1")
        }
        return
    }


    // remember permite recordar la lista mientras el nivel actual (conjunto de pares) no
    // ha terminado de desplegarse, mientras que shuffled() nos ayuda a desordenar los
    // elementos cada que cambiamos e nivel
    val claves = remember(nivelActual) {
        mutableStateListOf(*clavesTotales[nivelActual].shuffled().toTypedArray())
    }
    val valores = remember(nivelActual) {
        mutableStateListOf(*valoresTotales[nivelActual].shuffled().toTypedArray())
    }
    // Variables de selección
    var seleccionClave by remember { mutableStateOf<Pair<String, String>?>(null) }
    var seleccionValor by remember { mutableStateOf<Pair<String, String>?>(null) }


    // Validar selección
    if (seleccionClave != null && seleccionValor != null) {
        if (seleccionClave!!.first == seleccionValor!!.first) {
            claves.remove(seleccionClave)
            valores.remove(seleccionValor)
        }
        seleccionClave = null
        seleccionValor = null
        aciertos++
        calificacion=aciertos*25
    }

    // Mismo tamaño de card por fila :D
    val pares = claves.zip(valores)
    val tamañosPorFila = remember { mutableStateMapOf<Int, IntSize>() }


    // Se ejecuta únicamente mientras no se han terminado los niveles
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
        //Colocamos el cronometro, el parametro difultad hace
        //referencia a la cantidad de tiemo disponible para Jugar
        MarcadorTiempo(tiempo,60f)
        Espacio(16.dp)

        pares.forEachIndexed { index, (clave, valor) ->
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)

            ) {
                val alturaFila = with(LocalDensity.current) {
                    tamañosPorFila[index]?.height?.toDp() ?: Dp.Unspecified

                }

                // Card izquierda alinear dentro de la card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(alturaFila)
                        .padding(bottom = 8.dp) // espacio entre esta tarjeta y la siguiente
                        .onGloballyPositioned { coords ->
                            val size = coords.size
                            val prev = tamañosPorFila[index] ?: IntSize.Zero
                            tamañosPorFila[index] = IntSize(
                                width = maxOf(size.width, prev.width),
                                height = maxOf(size.height, prev.height)
                            )
                        }
                        .clickable { seleccionClave = clave },
                    colors = CardDefaults.cardColors(
                        containerColor = if (seleccionClave == clave) Color(0xFFC0EFD6) else Color(
                            0xFFFDF59A
                        ),
                        contentColor = Color(0xFF805315)
                    )
                ) {

                    Text(
                        text = clave.second,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }

                // Card derecha
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(alturaFila)
                        .padding(bottom = 8.dp) // espacio entre esta tarjeta y la siguiente
                        .onGloballyPositioned { coords ->
                            val size = coords.size
                            val prev = tamañosPorFila[index] ?: IntSize.Zero
                            tamañosPorFila[index] = IntSize(
                                width = maxOf(size.width, prev.width),
                                height = maxOf(size.height, prev.height)
                            )
                        }
                        .clickable { seleccionValor = valor },
                    colors = CardDefaults.cardColors(
                        containerColor = if (seleccionValor == valor) Color(0xFFC0EFD6) else Color(
                            0xFFFDF59A
                        ),
                        contentColor = Color(0xFF805315)
                    )
                ) {

                    Text(
                        text = valor.second,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

        }


        // Cuando se vacían ambos, cambia al siguiente nivel
        if (claves.isEmpty() && valores.isEmpty()) {
            tamañosPorFila.clear()
            nivelActual++
        }

    }
}




