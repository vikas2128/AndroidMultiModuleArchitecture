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

    protected val _messageState = MutableStateFlow<UIMessage?>(null)

    val messageState: StateFlow<UIMessage?>
        get() = _messageState
}
