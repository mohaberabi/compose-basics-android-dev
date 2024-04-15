package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.MarsApp
import com.example.marsphotos.data.MarsRepository
import com.example.marsphotos.model.Mars
import com.example.marsphotos.network.MarsService
import kotlinx.coroutines.launch

sealed interface MarsState {
    data class Success(val photos: List<Mars>) : MarsState
    data class Error(val error: String) : MarsState
    data object Loading : MarsState
    data object Initial : MarsState

}

class MarsViewModel(private val marsRepository: MarsRepository) : ViewModel() {
    var state: MarsState by mutableStateOf(MarsState.Initial)
        private set

    init {
        getMarsPhotos()
    }


    fun getMarsPhotos() {
        state = MarsState.Loading
        viewModelScope.launch {

            state = try {
                val result = marsRepository.getMarsPhotos()
                MarsState.Success(result)
            } catch (e: Exception) {

                MarsState.Error(e.toString())

            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsApp)
                val marsPhotosRepository = application.container.marsRepository
                MarsViewModel(marsRepository = marsPhotosRepository)
            }
        }
    }
}
