package com.per.ejemplo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.per.ejemplo.DataStore.VideoEstatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel(private val store: VideoEstatus) : ViewModel() {

    private val _videosVistos = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val videosVistos: StateFlow<Map<String, Boolean>> = _videosVistos

    fun cargarVideo(videoName: String) {
        viewModelScope.launch {
            store.isVideoVisto(videoName).collect { seen ->
                _videosVistos.value = _videosVistos.value.toMutableMap().apply {
                    this[videoName] = seen
                }
            }
        }
    }

    fun marcarVideoVisto(videoName: String) {
        viewModelScope.launch {
            store.setVideoVisto(videoName)
        }
    }
}
