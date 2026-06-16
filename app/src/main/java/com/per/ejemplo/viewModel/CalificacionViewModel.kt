package com.per.ejemplo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.per.ejemplo.DataStore.CalificacionEtapa

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CalificacionViewModel(private val store: CalificacionEtapa) : ViewModel() {

    private val _notaEtapa1 = MutableStateFlow(0)
    val notaEtapa1: StateFlow<Int> = _notaEtapa1

    private val _notaEtapa2 = MutableStateFlow(0)
    val notaEtapa2: StateFlow<Int> = _notaEtapa2

    private val _notaEtapa3 = MutableStateFlow(0)
    val notaEtapa3: StateFlow<Int> = _notaEtapa3

    private val _notaEtapa4 = MutableStateFlow(0)
    val notaEtapa4: StateFlow<Int> = _notaEtapa4


    fun cargarCalificacion(nombreEtapa: String) {
        viewModelScope.launch {
            store.consultaCalificacion(nombreEtapa).collect { valor ->
                when (nombreEtapa) {
                    "etapa1" -> _notaEtapa1.value = valor
                    "etapa2" -> _notaEtapa2.value = valor
                    "etapa3" -> _notaEtapa3.value = valor
                    "etapa4" -> _notaEtapa4.value = valor
                }
            }
        }
    }

    fun guardarCalificacion(nombreEtapa: String, valor: Int) {
        viewModelScope.launch {
            store.guardaCalificacion(nombreEtapa, valor)
        }
    }

}
