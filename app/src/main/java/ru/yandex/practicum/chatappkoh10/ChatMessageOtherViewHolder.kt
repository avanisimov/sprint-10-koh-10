package ru.yandex.practicum.chatappkoh10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yandex.practicum.chatappkoh10.databinding.ItemChatMessageOtherBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ChatMessageOtherViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_chat_message_other, parent, false)
) {

    private val binding: ItemChatMessageOtherBinding = ItemChatMessageOtherBinding.bind(itemView)

    private val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun bind(item: ChatMessage) {
        binding.messageTv.text = item.text
        binding.dateTv.text = formatter.format(item.date)
    }
}