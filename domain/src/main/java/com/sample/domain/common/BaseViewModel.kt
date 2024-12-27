package com.sample.domain.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {
    protected val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val loading: StateFlow<Boolean>
        get() = _loading

    private val _messageState = MutableStateFlow<Event<UIMessage>?>(null)

    val messageState: StateFlow<Event<UIMessage>?>
        get() = _messageState

    open suspend fun messageHandler(message: UIMessage) {
        withContext(Dispatchers.Main) {
            _messageState.value = Event(message)
        }
    }
}
