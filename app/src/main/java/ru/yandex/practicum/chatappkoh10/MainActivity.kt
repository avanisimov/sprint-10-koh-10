package ru.yandex.practicum.chatappkoh10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Secure
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.firestore
import ru.yandex.practicum.chatappkoh10.databinding.ActivityMainBinding
import java.lang.IllegalStateException
import java.util.Date

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val items = mutableListOf<Any>()
    private val chatAdapter = ChatAdapter(items)

    private val userID: String by lazy {
        Secure.getString(contentResolver, Secure.ANDROID_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val date = Date().apply {
            time = 1698776600005
        }
        Log.d("SPRINT_11", "userID = $userID")
        Log.d("SPRINT_11", "date = $date")

        val db = Firebase.firestore

        db.collection("chat_messages")
            .addSnapshotListener { value, error ->
                value?.documentChanges?.forEach {
                    if (it.type == DocumentChange.Type.ADDED) {
                        val text = it.document.getString("text")
                        val date = it.document.getLong("date")
                        val authorId = it.document.getString("author")
                        if (text != null && date != null && authorId !== null) {
                            val incomeChatMessage = ChatMessage(
                                text = text,
                                date = Date().apply {
                                    time = date
                                },
                                isSent = true,
                                author = if (authorId == userID) {
                                    Author.ME
                                } else {
                                    Author.OTHER
                                }
                            )

                            items.add(0, incomeChatMessage)
                            chatAdapter.notifyItemInserted(0)
                            binding.itemsRv.smoothScrollToPosition(0)
                        }
                    }
                }
            }

        binding.itemsRv.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, true)
            adapter = chatAdapter
        }

        binding.sendIv.setOnClickListener {
            val newMessageText = binding.newMessageEt.text.toString()
            val newMessage = ChatMessage(
                text = newMessageText,
                date = Date(),
                isSent = false,
                author = Author.ME
            )


            db.collection("chat_messages")
                .add(
                    hashMapOf(
                        "text" to newMessage.text,
                        "date" to newMessage.date.time,
                        "author" to userID,
                    )
                )
                .addOnSuccessListener {
                    Log.d("SPRINT_11", "collection add success $newMessage")
                }
            binding.newMessageEt.setText("")

//            binding.root.postDelayed({
//                val newMessageFromOther = ChatMessage(
//                    text = "ok, $newMessageText",
//                    date = Date(),
//                    isSent = false,
//                    author = Author.OTHER
//                )
//                items.add(0, newMessageFromOther)
//                chatAdapter.notifyItemInserted(0)
//                binding.itemsRv.smoothScrollToPosition(0)
//            }, 1000L)
        }
    }
}