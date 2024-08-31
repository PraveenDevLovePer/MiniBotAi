package com.techdevlp.minibotai.ui.home

import android.os.Build
import androidx.compose.runtime.toMutableStateList

class ChatBotUiState(
    messages: List<ChatMessage> = emptyList()
) {
    private val _messages: MutableList<ChatMessage> = messages.toMutableStateList()
    val messages: List<ChatMessage> = _messages

    fun addMessage(msg: ChatMessage) {
        _messages.add(msg)
    }

    fun replaceLastPendingMessage() {
        val lastMessage = _messages.lastOrNull()
        lastMessage?.let {
            val newMessage = lastMessage.apply { isPending = false }
            if (Build.VERSION.SDK_INT >= 35) {
                _messages.removeLast()
            }else {
                if (_messages.isNotEmpty()) {
                    _messages.removeAt(_messages.size - 1)
                }
            }
            _messages.add(newMessage)
        }
    }
}