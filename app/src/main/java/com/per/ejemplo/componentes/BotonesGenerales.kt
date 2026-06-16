package com.per.ejemplo.componentes

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.per.ejemplo.DataStore.CalificacionEtapa
import com.per.ejemplo.R
import com.per.ejemplo.viewModel.CalificacionViewModel
import com.per.ejemplo.viewModel.CalificacionViewModelFactory


@Composable
fun Espacio(altura: Dp) {
    Spacer(modifier = Modifier.height(altura))
}


@Composable
fun BotonDarkMode(darkMode: MutableState<Boolean>){
    Button(onClick = {
        /*La acción esta aquí*/
        darkMode.value=!darkMode.value
    },shape = RoundedCornerShape(8.dp),
        colors= ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color(0xFF805315))

    ) {
        Icon(painter = painterResource(id = R.drawable.luna120), contentDescription = "DarkMode", tint = Color.Unspecified)

    }
}


@Composable
fun BotonEtapa( nomEtapa: String, onClick: () -> Unit) {
    Button(
        onClick=onClick,
        shape = RoundedCornerShape(8.dp), colors= ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFDF59A), contentColor = Color(0xFF805315))
        ) { Icon(painter = painterResource(id = R.drawable.ic_book1), contentDescription = "Libro",
            tint = Color.Unspecified )
        Text(text = nomEtapa, fontWeight = FontWeight.Bold)
    }
}
@Composable
fun BotonClase(NomClase: String, onClick: () -> Unit) {
    Button(
        onClick=onClick,
        shape = RoundedCornerShape(8.dp),
        colors= ButtonDefaults.buttonColors(containerColor = Color(0xFFFDF59A),
            contentColor = Color(0xFF805315))
    ) {
        Icon(painter = painterResource(id = R.drawable.clase), // Icono de libro
            contentDescription = "clases", tint = Color.Unspecified)
        Text(text = NomClase, fontWeight = FontWeight.Bold)
    }
}



@Composable
fun BotonJuego(NomJuego: String, onClick: () -> Unit) {
    Button(
        onClick=onClick,
        shape = RoundedCornerShape(8.dp),
        colors= ButtonDefaults.buttonColors(
            containerColor = Color(0xFFC0EFD6),
            contentColor = Color(0xFF000000))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.videojuego), // Icono de libro
            contentDescription = "Juego",
            tint = Color.Unspecified

        )

        Text(
            text = NomJuego,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun BotonGuardarInformacion(NomClase: String, onClick: () -> Unit) {
    Button(
        onClick=onClick,
        shape = RoundedCornerShape(8.dp),
        colors= ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFDF59A),
            contentColor = Color(0xFF805315)),
                modifier = Modifier.fillMaxWidth(),

    ) {

        Text(
            text = NomClase,
            fontWeight = FontWeight.Bold
        )
    }
}

/*@Composable
fun BotonVerde(NomClase: String, onClick: () -> Unit) {
    Button(
        onClick=onClick,
        shape = RoundedCornerShape(8.dp),
        colors= ButtonDefaults.buttonColors(
            containerColor = Color(0xFFB4E5A2),
            contentColor = Color(0xFF100F0F)
        ),
        modifier = Modifier.weight(1f)   // Cada botón ocupa un tercio del ancho
            .padding(8.dp) //  Espaciado entre botones

        ) {

        Text(
            text = NomClase,
            fontWeight = FontWeight.Bold
        )
    }
}*/

@Composable
fun BotonVerde(NomClase: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFB4E5A2),
            contentColor = Color.Black
        ),
        modifier = modifier
    ) {
        Text(
            text = NomClase,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(4.dp),
            fontSize = 12.sp,
            textAlign = TextAlign.Center)

    }
}


// cambiar por botón tablas
@Composable
fun BotonTablas(NomModalidad: String, onClick: () -> Unit) {
    Button(
        onClick=onClick,
        shape = RoundedCornerShape(8.dp),
        //border = BorderStroke(2.dp, Color(0xFF805315)), // Borde marrón
        colors= ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color(0xFF805315)),
       contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp), // Espaciado interno

    ) {
        Text(
            text = NomModalidad,
            fontWeight = FontWeight.Bold,

            )

        Icon(

            painter = painterResource(id = R.drawable.archivos), // Icono de libro
            contentDescription = "Modalidad",
            tint = Color.Unspecified,
            modifier = Modifier.size(40.dp)

        )
    }
}


@Composable
fun BotonInformacion(navegacion: String, onClick: () -> Unit) {
    Button(
        onClick=onClick,
        shape = RoundedCornerShape(8.dp),
        //border = BorderStroke(2.dp, Color(0xFF805315)), // Borde marrón
        colors= ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color(0xFF805315)),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp), // Espaciado interno
    ) {

        Icon(

            painter = painterResource(id = R.drawable.palomita10), // Icono de bote palomitas
            contentDescription = "Informacion",
            tint = Color.Unspecified
           // modifier = Modifier.size(60.dp)

        )
    }
}




@Composable
fun PuntajeGeneral(puntaje: String, seccion: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color(0xFFCA9804), fontSize = 26.sp,fontWeight = FontWeight.Bold)) {
                append(puntaje + "/1000\n") // Puntaje en ron
            }
            withStyle(style = SpanStyle(color = Color(0xFF85560E),fontSize = 22.sp ,fontWeight = FontWeight.Bold)) {
                append(seccion) // Texto marrón
            }
        },
        textAlign = TextAlign.Center
    )
}




@Composable
fun AvancePorcentaje(avance: String) {

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp)) {
                append(avance)
            }
        },
        textAlign = TextAlign.Center
    )
}

@Composable
fun MarcadorClase(terminado: Boolean) {

    var  texto ="¡Toma la clase!"
    if(terminado){
        texto="¡Visto!"
    }

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp)) {
                append(texto)
            }
        }
    )


}

/// barra de avance

@Composable
fun BarraProgreso(progreso: Float, anchoBarra: Dp) {
    LinearProgressIndicator(
        progress = {
            progreso // Progreso entre 0f y 1f
        },
        modifier = Modifier
            .width(anchoBarra) // Ancho personalizado
            .height(5.dp)
            .clip(RoundedCornerShape(5.dp)), // Esquinas redondeadas
        color = Color(0xFF805315), // Color de la barra
        trackColor = Color(0xFFFDF59A), // Color de fondo
    )
}


// Ejemplo de topbar
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    TopAppBar(
        title = { Text("Mi App") },
        navigationIcon = { // Ícono a la izquierda (Menú)
            IconButton(onClick = { /* Acción del menú */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menú"
                )
            }
        },
        actions = { // Íconos a la derecha
            IconButton(onClick = { /* Acción de búsqueda */ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            }

            IconButton(onClick = { /* Acción de notificaciones */ }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificaciones"
                )
            }
            IconButton(onClick = { /* Acción de configuración */ }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configuración"
                )
            }
        }
    )


}


@Composable
fun BotonRegresar(onClick: () -> Unit){
    IconButton(onClick=onClick) {
        Icon(imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = "regresar",
            tint= Color(0xFF805315)
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(navController: NavController,puntos:String, sector: String) {

    if (sector.contentEquals("calificación general")) {

        CenterAlignedTopAppBar(
            title = { PuntajeGeneral(puntaje = puntos, seccion = sector) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFFFDF59A) // color de la barra
            ),

        )
    }else{
        CenterAlignedTopAppBar(
            title = { PuntajeGeneral(puntaje = puntos, seccion = sector) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFFFDF59A) // color de la barra
            ),
            navigationIcon ={
                BotonRegresar(){
                    navController.popBackStack()
                }
            }
        )
    }
}

@Composable
fun BarraInferior( darkMode: MutableState<Boolean>, navController: NavController) {

    val destinoActual = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = Color(0xFFFDF59A) // Color de fondo de la barra
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho disponible
            horizontalArrangement = Arrangement.SpaceEvenly, // Distribuye uniformemente
            verticalAlignment = Alignment.CenterVertically // Centra los botones verticalmente
        ) {
            //BotonModalidad(NomModalidad = "") { }
            BotonDarkMode(darkMode = darkMode)

            // Segundo ícono - Modalidad
            BotonTablas(NomModalidad = "") {

                if( destinoActual=="TablasInformativas"){
                    navController.popBackStack()
                }else{
                    navController.navigate("TablasInformativas")
                }

            }

            // Tercer ícono - Información
            BotonInformacion (navegacion = "") {


                if( destinoActual=="InformacionGeneral"){
                    navController.popBackStack()
                }else{
                    navController.navigate("InformacionGeneral")
                }
            }
        }
    }
}


/*@Composable
fun BarraInferiorVideos( navController: NavController) {

    NavigationBar(
        containerColor = Color(0xFFFDF59A) // Color de fondo de la barra
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho disponible
            horizontalArrangement = Arrangement.SpaceEvenly, // Distribuye uniformemente
            verticalAlignment = Alignment.CenterVertically // Centra los botones verticalmente
        ) {
            BotonRegresar(){
                navController.popBackStack()
            }

        }
    }
}*/

@Composable
fun BarraInferiorVideos(navController: NavController) {

    BottomAppBar(
        containerColor = Color(0xFFFDF59A),
        modifier = Modifier.height(45.dp)   // ⬅️ Tamaño reducido
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BotonRegresar { navController.popBackStack() }
        }
    }
}

@Composable
fun Boton(navegacion: String, onClick: () -> Unit) {
    Button(
        onClick=onClick,
        shape = RoundedCornerShape(8.dp),
        //border = BorderStroke(2.dp, Color(0xFF805315)), // Borde marrón
        colors= ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color(0xFF805315)),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp), // Espaciado interno
    ) {

        Icon(

            painter = painterResource(id = R.drawable.palomita10), // Icono de bote palomitas
            contentDescription = "Informacion",
            tint = Color.Unspecified
            // modifier = Modifier.size(60.dp)

        )
    }
}


@Composable
fun BotonFlotante(navController: NavController){
    FloatingActionButton(onClick = {  navController.popBackStack()},
        containerColor = Color.Green,
        contentColor = Color.White)
    {
        Icon(

            Icons.Filled.ArrowBackIosNew,
            contentDescription = "",
            tint = Color(0xFFFDF59A),
            modifier = Modifier.size(50.dp))
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
fun MarcadorTiempo(tiempo: Int , dificultad: Float){

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(

            painter = painterResource(id = R.drawable.reloj), // Icono reloj
            contentDescription = "Informacion",
            tint = Color.Unspecified,
            modifier = Modifier.size(60.dp)

        )

        LinearProgressIndicator(
            progress = {
                tiempo.toFloat()/dificultad // dificultad es la cantidad de tiempo
                // que se tiene para completar el juego
            },
            modifier = Modifier
                .width(250.dp) // Ancho personalizado
                .height(15.dp)
                .clip(RoundedCornerShape(5.dp)), // Esquinas redondeadas
            color = Color(0xFFC0EFD6), // Color de la barra
            trackColor = Color(0xFFFDF59A), // Color de fondo
        )

        Text(
            text = " $tiempo s",
            fontWeight = FontWeight.Bold

        )
    }
}


@Composable
fun MarcadorTiempoRegresivo(tiempo: Int, dificultad: Float) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = R.drawable.reloj),
            contentDescription = "Informacion",
            tint = Color.Unspecified,
            modifier = Modifier.size(60.dp)
        )

        LinearProgressIndicator(
            progress = {
                (tiempo.toFloat() / dificultad)
                    .coerceIn(0f, 1f)   // Asegura valores válidos entre 0 y 1
            },
            modifier = Modifier
                .width(220.dp)
                .height(15.dp)
                .clip(RoundedCornerShape(5.dp)),
            color = Color(0xFFC0EFD6),
            trackColor = Color(0xFFFDF59A)
        )

        Text(
            text = " $tiempo s",
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun MarcadorFinal(aciertos: Int, calificacion: Int,
                  context: Context, navController: NavController, nomClase:String){

    val store = remember { CalificacionEtapa(context) }
    val viewModel: CalificacionViewModel = viewModel(factory = CalificacionViewModelFactory(store))

    val mensaje= listOf(
        "Impresionante, lo estas dominando como nadie",
        "Lo estas haciendo muy bien",
        "Cometer errores es parte del camino hacia el éxito"
    )

    val mensajeFinal = when {
        calificacion >= 900 -> mensaje[0]
        calificacion >= 600 -> mensaje[1]
        else -> mensaje[2]
    }
    val icono = when {
        calificacion >= 900 -> R.drawable.estrella910
        calificacion >= 600 -> R.drawable.estrella678
        else -> R.drawable.estrella543210
    }


Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
){

    //Espacio para los aciertos
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(

            painter = painterResource(id = R.drawable.pulgar), // Icono de bote palomitas
            contentDescription = "Acierto",
            tint = Color.Unspecified,
            modifier = Modifier.size(150.dp)
                .padding(16.dp)// hay que modificar segun se vea en vista

        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(100.dp) // O el alto que quieras
                .background(Color(0xFFFDF59A),
                    shape = RoundedCornerShape(16.dp)), // Color de fondo, puedes usar cualquier color
            contentAlignment = Alignment.Center // Centra el contenido dentro de la Box
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Total, de aciertos:", color = Color(0xFF805315), fontSize = 24.sp,fontWeight = FontWeight.Bold)
                Text(text = "$aciertos", color = Color(0xFF805315), fontSize = 24.sp,fontWeight = FontWeight.Bold)
            }
        }

    }

    //Espacio para la calificación
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(

            painter = painterResource(id = icono), // Icono de bote palomitas
            contentDescription = "Calificacion",
            tint = Color.Unspecified,
            modifier = Modifier.size(150.dp)
                .padding(16.dp)// hay que modificar segun se vea en vista// hay que modificar segun se vea en vista

        )
        Box(
            modifier = Modifier
                .fillMaxWidth() // O el tamaño que necesites
                .padding(16.dp)
                .height(100.dp) // O el alto que quieras
                .background(Color(0xFFFDF59A),
                    shape = RoundedCornerShape(16.dp)), // Color de fondo, puedes usar cualquier color
            contentAlignment = Alignment.Center // Centra el contenido dentro de la Box
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Calificación:", color = Color(0xFF805315), fontSize = 24.sp,fontWeight = FontWeight.Bold)
                Text(text = "$calificacion", color = Color(0xFF805315), fontSize = 24.sp,fontWeight = FontWeight.Bold)
            }
        }

    }

    //Espacio para los mensajes
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){

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
                Text(text = mensajeFinal, color = Color.Black, fontSize = 20.sp,fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center)
                Text(text = "¡Sigue Adelante!", color = Color.Black, fontSize = 24.sp,fontWeight = FontWeight.Bold)
            }
        }

    }

    //Espacio para el Botón
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){

        Box(
            modifier = Modifier
                .fillMaxWidth() // O el tamaño que necesites
                .padding(16.dp)
                .height(80.dp) // O el alto que quieras
                .background(Color(0xFFB4E5A2),
                    shape = RoundedCornerShape(16.dp) )// Color de fondo, puedes usar cualquier color
            .clickable {
            // guardamos en base y regresamos
                viewModel.guardarCalificacion(nomClase, calificacion)
                navController.popBackStack()

            println("Box presionada")
        }
            .padding(16.dp),
            contentAlignment = Alignment.Center // Centra el contenido dentro de la Box
        ) {
            Text(text = "Guardar nota y continuar", color = Color.Black, fontSize = 22.sp,fontWeight = FontWeight.Bold)
        }

    }
 }

}

@Composable
fun BotonGuardarNota(onClick: () -> Unit){

    Button(
            onClick=onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors= ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB4E5A2),
                contentColor = Color(0xFF000000))
        ) {

            Text(
                text = "Guardar nota y continuar",
                fontWeight = FontWeight.Bold
            )
        }

}


///Para cambiar colores en general es mejor cambiarlo desde el principal
/// ver de nuevo clase 43