package com.per.ejemplo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.per.ejemplo.DataStore.CalificacionEtapa

class CalificacionViewModelFactory(
    private val store: CalificacionEtapa
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalificacionViewModel(store) as T
    }
}