package ru.yandex.practicum.chatappkoh10

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.lang.IllegalStateException

class ChatAdapter(
    private val items: List<Any>
) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = items[position]) {
            is ChatMessage -> {
                when (item.author) {
                    Author.ME -> VIEW_TYPE_MESSAGE_ME
                    Author.OTHER -> VIEW_TYPE_MESSAGE_OTHER
                }
            }
            is ChatDayDivider -> VIEW_TYPE_DAY_DIVIDER
            else -> throw IllegalStateException()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("SPRINT_11", "onCreateViewHolder viewType=$viewType")
        return when (viewType) {
            VIEW_TYPE_MESSAGE_ME -> ChatMessageMeViewHolder(parent)
            VIEW_TYPE_MESSAGE_OTHER -> ChatMessageOtherViewHolder(parent)
            VIEW_TYPE_DAY_DIVIDER -> TODO()
            else -> throw IllegalStateException()
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ChatMessageMeViewHolder -> {
                holder.bind(items[position] as ChatMessage)
            }

            is ChatMessageOtherViewHolder -> {
                holder.bind(items[position]as ChatMessage)
            }
        }
    }

    companion object {
        const val VIEW_TYPE_MESSAGE_ME = 1
        const val VIEW_TYPE_MESSAGE_OTHER = 2
        const val VIEW_TYPE_DAY_DIVIDER = 3
    }
}