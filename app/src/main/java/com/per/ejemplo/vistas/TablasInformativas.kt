package com.per.ejemplo.vistas

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.per.ejemplo.componentes.BarraInferior
import com.per.ejemplo.componentes.Espacio





@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TablasInformativas(navController: NavController, darkMode: MutableState<Boolean>){

    Scaffold(
        bottomBar = { BarraInferior(darkMode, navController) }
    ) { innerPadding ->
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
            ContenidoTablasInformativas()
        }
    }
}


@Composable
fun ContenidoTablasInformativas() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp), // separación adicional del borde superior
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { TablaCard(titulo = "Tabla de conversiones numéricas") { TablaNumeros() } }
        item { TablaCard(titulo = "Compuerta NOT") { TablaNot() } }
        item { TablaCard(titulo = "Compuerta AND") { TablaCompuertaGeneral(1) } }
        item { TablaCard(titulo = "Compuerta 0R") { TablaCompuertaGeneral(2) } }
        item { TablaCard(titulo = "Compuerta NAND") { TablaCompuertaGeneral(3) } }
        item { TablaCard(titulo = "Compuerta NOR") { TablaCompuertaGeneral(4) } }
        item { TablaCard(titulo = "Compuerta XOR") { TablaCompuertaGeneral(5) } }
        item { TablaCard(titulo = "Compuerta XNOR") { TablaCompuertaGeneral(6) } }
        item { TablaCard(titulo = "Leyes del álgebra de Boole") { TablaAlgebra() } }

    }
}


@Composable
fun TablaNumeros() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color(0xFF805315), RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(
            "Tabla de números (Binario, Octal, Hexadecimal)",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF805315)
        )
        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("Dec", fontWeight = FontWeight.Bold)
            Text("Bin", fontWeight = FontWeight.Bold)
            Text("Oct", fontWeight = FontWeight.Bold)
            Text("Hex", fontWeight = FontWeight.Bold)
        }

        for (i in 0..15) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text("$i")
                Text(Integer.toBinaryString(i))
                Text(Integer.toOctalString(i))
                Text(Integer.toHexString(i).uppercase())
            }
        }
    }
}


@Composable
fun TablaAlgebra() {
    val reglas = listOf(
        "A + 0 = A",
        "A + 1 = 1 ",
        "A · 0 = 0",
        "A · 1 = A",
        "A + A = A",
        "A + Ā = 1",
        "A · A = A",
        "A · Ā = 0",
        "A + AB = A",
        "A + ĀB = A + B",
        "(A + B)(A + C) = A +BC",
        "(A + B)' = A' · B' (DeMorgan)",
        "(A · B)' = A' + B' (DeMorgan)"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color(0xFF805315), RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // centramos contenido
    ) {
        Spacer(Modifier.height(8.dp))

        reglas.forEach { regla ->
            Text(
                text = regla,
                color = Color(0xFF805315),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun TablaNot() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color(0xFF805315), RoundedCornerShape(8.dp)) // Borde externo
            .padding(8.dp)
    ) {
        // Título de la tabla
        Text(
            text = "Tabla de verdad de la compuerta NOT",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF805315),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Encabezado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF805315)) // borde del encabezado
                .background(Color(0xFFFFF7D1)), // fondo suave para destacar encabezado
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CeldaTexto("A (entrada)", true)
            CeldaTexto("Y (salida)", true)
        }


        // Filas de datos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF805315)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CeldaTexto("0")
            CeldaTexto("1")
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF805315)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CeldaTexto("1")
            CeldaTexto("0")
        }
    }
}


@Composable
fun TablaCompuertaGeneral(nCompuerta: Int) {


    val salidaY = Array<String>(4) { "" }
    var tablaVerdad= ""
    if(nCompuerta==1){
        tablaVerdad="Tabla de verdad de la compuerta AND"
        salidaY.set(0,"0")
        salidaY.set(1,"0")
        salidaY.set(2,"0")
        salidaY.set(3,"1")
    }
    if(nCompuerta==2){
        tablaVerdad="Tabla de verdad de la compuerta OR"
        salidaY.set(0,"0")
        salidaY.set(1,"1")
        salidaY.set(2,"1")
        salidaY.set(3,"1")
    }
    //NAND
    if(nCompuerta==3){
        tablaVerdad="Tabla de verdad de la compuerta NAND"
        salidaY.set(0,"1")
        salidaY.set(1,"1")
        salidaY.set(2,"1")
        salidaY.set(3,"0")
    }
    //NOR
    if(nCompuerta==4){
        tablaVerdad="Tabla de verdad de la compuerta NOR"
        salidaY.set(0,"1")
        salidaY.set(1,"0")
        salidaY.set(2,"0")
        salidaY.set(3,"0")
    }
    //XOR
    if(nCompuerta==5){
        tablaVerdad="Tabla de verdad de la compuerta XOR"
        salidaY.set(0,"0")
        salidaY.set(1,"1")
        salidaY.set(2,"1")
        salidaY.set(3,"0")
    }
    //XNOR
    if(nCompuerta==6){
        tablaVerdad="Tabla de verdad de la compuerta XNOR"
        salidaY.set(0,"1")
        salidaY.set(1,"0")
        salidaY.set(2,"0")
        salidaY.set(3,"1")
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color(0xFF805315), RoundedCornerShape(8.dp)) // Borde externo
            .padding(8.dp)
    ) {
        // Título de la tabla
        Text(
            text = tablaVerdad,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF805315),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Encabezado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF805315)) // borde del encabezado
                .background(Color(0xFFFFF7D1)), // fondo suave para destacar encabezado
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CeldaTexto("A (entrada)", true)
            CeldaTexto("B (entrada)", true)
            CeldaTexto("Y (salida)", true)
        }

        // Filas de datos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF805315)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CeldaTexto("0")
            CeldaTexto("0")
            CeldaTexto(salidaY[0])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF805315)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CeldaTexto("0")
            CeldaTexto("1")
            CeldaTexto(salidaY[1])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF805315)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CeldaTexto("1")
            CeldaTexto("0")
            CeldaTexto(salidaY[2])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF805315)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CeldaTexto("1")
            CeldaTexto("1")
            CeldaTexto(salidaY[3])
        }
    }
}


@Composable
fun RowScope.CeldaTexto(texto: String, esEncabezado: Boolean = false) {
    Text(
        text = texto,
        fontWeight = if (esEncabezado) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier
            .weight(1f)
            .padding(8.dp)
            .border(0.5.dp, Color(0xFF805315)),
        textAlign = TextAlign.Center,
        color = Color(0xFF805315)
    )
}






@Composable
fun TablaCard(titulo: String, contenido: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x80FFF9C4)) // Fondo suave
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = titulo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF805315),
                textAlign = TextAlign.Center
            )
            Espacio(10.dp)
            contenido()
        }
    }
}