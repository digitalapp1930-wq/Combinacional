package com.per.ejemplo.vistas

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.per.ejemplo.componentes.BarraInferior
import com.per.ejemplo.componentes.BotonGuardarInformacion

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InformacionGeneral(navController: NavController, darkMode: MutableState<Boolean>){

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
            InfoUniversidadScreen()
        }
    }
}

@Composable
fun InfoUniversidadScreen() {

    var correo by remember { mutableStateOf("") }
    var universidadSeleccionada by remember { mutableStateOf("") }
    var expandirLista by remember { mutableStateOf(false) }

    val listaUniversidades = listOf(
        "BUAP",
        "UNAM",
        "IPN",
        "UAM",
        "Tecnológico de Monterrey"
    )

    val versionApp = "Versión 1.0"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            "Registro",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(Modifier.height(16.dp))

        // Campo de texto para correo
        Text(text = "Correo personal")
        androidx.compose.material3.TextField(
            value = correo,
            onValueChange = { correo = it },
            placeholder = { Text("ejemplo@correo.com") },
            modifier = Modifier.fillMaxWidth(),
           /* colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White )*/
            )

        Spacer(Modifier.height(16.dp))

        Text("Universidad")

        Box {
            // Caja visual seleccionada
            androidx.compose.material3.TextField(
                value = universidadSeleccionada,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                /*colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White ),*/
                placeholder = { Text("Selecciona tu universidad") }
            )

            // Cuando se da clic, muestra el menú
            androidx.compose.material3.DropdownMenu(
                expanded = expandirLista,
                onDismissRequest = { expandirLista = false }
            ) {
                listaUniversidades.forEach { uni ->
                    androidx.compose.material3.DropdownMenuItem(
                        text = { Text(uni) },
                        onClick = {
                            universidadSeleccionada = uni
                            expandirLista = false
                        }
                    )
                }
            }

            // Botón invisible (overlay) para abrir la lista
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { expandirLista = true }
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            "Información de la aplicación",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )

        Spacer(Modifier.height(8.dp))

        Text(" $versionApp")
        Text(" Última actualización: Enero 2025")


        Spacer(Modifier.height(30.dp))

        BotonGuardarInformacion("Guardar información"){}


    }
}
