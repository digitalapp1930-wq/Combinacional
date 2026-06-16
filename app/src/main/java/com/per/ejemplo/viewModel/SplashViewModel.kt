package com.per.ejemplo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SplashState {
    object Loading : SplashState()
    object Finished : SplashState()
}

class SplashViewModel : ViewModel() {

    private val _state = MutableStateFlow<SplashState>(SplashState.Loading)
    val state = _state.asStateFlow()

    init {
        // Duración del video (4s )
        viewModelScope.launch {
            delay(4000)
            _state.value = SplashState.Finished
        }
    }
}