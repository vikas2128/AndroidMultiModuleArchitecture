package com.sample.domain.common

data class UIMessage(var message: String, val type: UIMessageType)

enum class UIMessageType {
    TOAST, SNACKBAR, DIALOG, NONE
}
