package com.sample.home.common

import androidx.lifecycle.ViewModel
import com.sample.domain.common.UIMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {
    protected val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val loading: StateFlow<Boolean>
        get() = _loading

    protected val _messageState = MutableStateFlow<UIMessage?>(null)

    val messageState: StateFlow<UIMessage?>
        get() = _messageState
}
