package com.createfuture.takehome.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.createfuture.takehome.domain.model.Character
import com.createfuture.takehome.domain.usecase.GetCharactersUseCase
import com.google.gson.JsonParseException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.collections.filter
import kotlin.text.contains

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DeviceListState>(DeviceListState.Loading)
    val uiState: StateFlow<DeviceListState> = _uiState
    private var allDevices: List<Character> = emptyList()

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            try {
                allDevices = getCharactersUseCase()
                _uiState.value = DeviceListState.Success(allDevices)
            } catch (e: JsonParseException) {
                _uiState.value = DeviceListState.Error("It's not you! something's wrong with our server.")
            } catch (e: UnknownHostException) {
                _uiState.value = DeviceListState.Error("Please check your internet connection")
            } catch (e: Exception) {
                _uiState.value = DeviceListState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun searchDevices(query: String) {
        val currentState = _uiState.value
        if (currentState is DeviceListState.Success) {
            val filtered = allDevices.filter {
                it.name.contains(query, ignoreCase = true)
            }
            _uiState.value = DeviceListState.Success(filtered)
        }
    }
}


sealed class DeviceListState {
    data object Loading : DeviceListState()
    data class Success(val characters: List<Character>) : DeviceListState()
    data class Error(val message: String) : DeviceListState()
}