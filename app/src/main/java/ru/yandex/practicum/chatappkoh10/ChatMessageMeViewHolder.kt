package ru.yandex.practicum.chatappkoh10

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yandex.practicum.chatappkoh10.databinding.ItemChatMessageMeBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ChatMessageMeViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_chat_message_me, parent, false)
) {

    private val binding: ItemChatMessageMeBinding = ItemChatMessageMeBinding.bind(itemView)

    private val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun bind(item: ChatMessage) {
        Log.d("SPRINT_11", "$this bind $item")
        binding.messageTv.text = item.text
        binding.dateTv.text = formatter.format(item.date)
    }
}