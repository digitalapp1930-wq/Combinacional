package com.per.ejemplo.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.per.ejemplo.vistas.Etapa1_Juego1
import com.per.ejemplo.vistas.Etapa2_Juego2
import com.per.ejemplo.vistas.Etapa3_Juego3
import com.per.ejemplo.vistas.Etapa4_Juego4
import com.per.ejemplo.vistas.SplashScreen
import com.per.ejemplo.vistas.HomeEtapa1
import com.per.ejemplo.vistas.HomeEtapa2
import com.per.ejemplo.vistas.HomeEtapa3
import com.per.ejemplo.vistas.HomeEtapa4
import com.per.ejemplo.vistas.Homeview
import com.per.ejemplo.vistas.InformacionGeneral
import com.per.ejemplo.vistas.PantallaVideo
import com.per.ejemplo.vistas.TablasInformativas


@Composable
fun NavegaManager (darkMode: MutableState<Boolean>){
    val navController= rememberNavController()
    NavHost(navController=navController, startDestination ="splash"){
        //Para redirigir a la SplashScreen
        composable("splash") { SplashScreen(navController) }
        //Para redirigir a la pagina inicial
        composable("Home"){ Homeview(LocalContext.current,navController,darkMode) }
        //para ir a la etapa 1
        composable("Etapa1"){ HomeEtapa1(LocalContext.current,navController,darkMode) }

        // para la etapa 2
        composable("Etapa2"){
            HomeEtapa2(LocalContext.current,navController,darkMode)
        }

        // para la etapa 3
        composable("Etapa3"){
            HomeEtapa3(LocalContext.current,navController,darkMode)
        }
        // para la etapa 4
        composable("Etapa4"){
            HomeEtapa4(LocalContext.current,navController,darkMode)
        }

        //para ir al juego de la etapa 1
        composable("GameEtapa1") {
            Etapa1_Juego1(LocalContext.current,navController,darkMode)
        }
        //para ir al juego de la etapa 2
        composable("GameEtapa2") {
            Etapa2_Juego2(LocalContext.current,navController,darkMode)
        }
        //para ir al juego de la etapa 3
        composable("GameEtapa3") {
            Etapa3_Juego3(LocalContext.current,navController,darkMode)
        }

        //para ir al juego de la etapa 4
        composable("GameEtapa4") {
            Etapa4_Juego4(LocalContext.current,navController,darkMode)
        }

        composable("TablasInformativas") {
            TablasInformativas(navController,darkMode)
        }

        //InformacionGeneral
        composable("InformacionGeneral") {
            InformacionGeneral(navController,darkMode)
        }
        //Videos
        composable(route = "pantallaVideo/{videoName}") { backStackEntry ->
            val videoName = backStackEntry.arguments?.getString("videoName") ?: ""
            PantallaVideo(
                context = LocalContext.current,
                navController = navController,
                videoName = videoName)
        }
    }
}

