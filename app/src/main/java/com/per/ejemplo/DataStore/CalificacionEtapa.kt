package com.per.ejemplo.DataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CalificacionEtapa(private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore("calificacion_etapa")
    }

    // KEY dinámica pero ahora de tipo INT
    private fun getKeyForCalificacion(nombreEtapa: String) =
        intPreferencesKey("calificacion_$nombreEtapa")

    //  devuelve un Flow<Int>
    fun consultaCalificacion(nombreEtapa: String): Flow<Int> =
        context.dataStore.data.map { prefs ->
            prefs[getKeyForCalificacion(nombreEtapa)] ?: 0   // valor por defecto = 0
        }

    // Guardar una calificación numérica
    suspend fun guardaCalificacion(nombreEtapa: String, valor: Int) {
        context.dataStore.edit { prefs ->
            prefs[getKeyForCalificacion(nombreEtapa)] = valor
        }
    }
}

