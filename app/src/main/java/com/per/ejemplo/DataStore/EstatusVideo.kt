package com.per.ejemplo.DataStore
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VideoEstatus(private val context: Context) {

    //Creamos  un DataStore asociado al contexto de la app.
    //Donde preferencesDataStore("video_estatus") es
    // el nombre del archivo donde se guardarán los datos.
    companion object {
        private val Context.dataStore by preferencesDataStore("video_estatus")
    }

    // Generamos dinámicamente la KEY según el nombre del video
    private fun getKeyForVideo(videoName: String) =
        booleanPreferencesKey("VIDEO_VISTO_$videoName")

    // Obtenemos la información para saber si el video ya fue visto
    fun isVideoVisto(videoName: String): Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[getKeyForVideo(videoName)] ?: false
        }

    // Marcamos un video como visto
    suspend fun setVideoVisto(videoName: String) {
        context.dataStore.edit { prefs ->
            prefs[getKeyForVideo(videoName)] = true
        }
    }
}
