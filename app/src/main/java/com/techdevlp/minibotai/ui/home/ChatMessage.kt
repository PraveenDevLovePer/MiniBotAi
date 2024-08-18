package com.techdevlp.minibotai.ui.home

import java.util.UUID

enum class Participant {
    YOU, MINIBOT, ERROR
}

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    var text: String = "",
    val participant: Participant = Participant.YOU,
    var isPending: Boolean = false
)