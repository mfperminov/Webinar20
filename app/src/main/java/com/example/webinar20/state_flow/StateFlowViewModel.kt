package com.example.webinar20.state_flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StateFlowViewModel : ViewModel() {

    private val _screenStateFlow = MutableStateFlow(ScreenState(0, "Пока ничего"))
    val screenStateFlow = _screenStateFlow.asStateFlow()

    init {
        requestServer()
    }

    fun increment() {
        _screenStateFlow.update {
            it.copy(
                value = it.value + 1,
                message = "опа плюс один"
            )
        }
    }

    fun decrement() {
        _screenStateFlow.update {
            it.copy(
                value = it.value - 1,
                message = "минус один("
            )
        }
    }

    private fun requestServer() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(10000L)
            _screenStateFlow.update {
                it.copy(
                    value = it.value + 5,
                    message = "Пришел ответ с сервера плюс пять"
                )
            }
        }
    }

}

data class ScreenState(val value: Int, val message: String?)