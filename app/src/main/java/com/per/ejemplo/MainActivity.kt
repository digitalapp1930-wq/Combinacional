package com.per.ejemplo
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.per.ejemplo.componentes.*
import com.per.ejemplo.navegacion.NavegaManager
import com.per.ejemplo.ui.theme.EjemploTheme2
import com.per.ejemplo.vistas.ContenidoEtapa4
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
          val darkMode= remember { mutableStateOf(false) }
            EjemploTheme2(
                darkTheme = darkMode.value
            ) {
                Surface (modifier = Modifier.fillMaxSize(),
                    color= MaterialTheme.colorScheme.background)
                {
                    // Aquí comenzamos a trabajar
                    NavegaManager(darkMode=darkMode)
                }
            }
        }
    }
}

//GreetingPreview()
//Pruebas de elemento
//@Preview(showBackground = true)
//@Composable
/*fun GreetingPreview() {
    EjemploTheme2(darkTheme = false) {
        Column (modifier= Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
            BotonEtapa("Botón generico para las etapas"){}
            Spacer(modifier = Modifier.height(10.dp))
            BotonClase("Botón generico para las clases"){}
            Spacer(modifier = Modifier.height(10.dp))
            BotonJuego ("Botón generico para juegos"){}



        }
    }
}*/

















