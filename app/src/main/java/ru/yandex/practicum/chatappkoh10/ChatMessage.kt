package ru.yandex.practicum.chatappkoh10

import java.util.Date

data class ChatMessage(
    val text: String,
    val date: Date,
    val isSent: Boolean,
    val author: Author,
)

enum class Author {
    ME, OTHER
}