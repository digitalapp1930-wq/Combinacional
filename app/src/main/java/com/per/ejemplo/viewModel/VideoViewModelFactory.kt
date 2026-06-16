package com.per.ejemplo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.per.ejemplo.DataStore.VideoEstatus

class VideoViewModelFactory(
    private val store: VideoEstatus
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideoViewModel(store) as T
    }
}

