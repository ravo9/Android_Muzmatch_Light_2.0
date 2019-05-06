package development.dreamcatcher.muzmatchlight.activities

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import development.dreamcatcher.muzmatchlight.R
import development.dreamcatcher.muzmatchlight.adapters.MessageAdapter
import development.dreamcatcher.muzmatchlight.models.message.MessageEntity
import development.dreamcatcher.muzmatchlight.viewmodels.ChatViewModel
import kotlinx.android.synthetic.main.activity_chat.*
import java.text.SimpleDateFormat


class ChatActivity : AppCompatActivity() {

    private lateinit var viewModel: ChatViewModel
    private val messagesAdapter = MessageAdapter()
    private val dateTimeInstance = SimpleDateFormat.getDateTimeInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)

        // Initialize RecyclerView
        recyclerView_messages.adapter = messagesAdapter
        recyclerView_messages.layoutManager = LinearLayoutManager(this)

        // Observe data provided by ViewModel
        viewModel.getAllMessages().observe(this, Observer<List<MessageEntity>> { messages ->

            // Upload fake messages as an initial input to mock another user's messages
            if (messages.isEmpty()) {
                viewModel.uploadFakeInitialMessages(dateTimeInstance)
            }

            // Upload stored messages
            if (messagesAdapter.itemCount == 0) {
                messagesAdapter.setMessages(messages)
            }
        })

        editText_messageInput.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                // Perform message sending
                addNewMessage()

                // Scroll recycler view after message is added
                recyclerView_messages.scrollToPosition(messagesAdapter.itemCount - 1)

                // Keep focus on the message input
                editText_messageInput.requestFocus()

                return@OnKeyListener true
            }
            false
        })

        // Scroll the recycler view when Soft Input (keyboard) is triggered to be displayed
        recyclerView_messages.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            if (bottom < oldBottom) {
                recyclerView_messages.postDelayed({
                    recyclerView_messages.scrollToPosition(messagesAdapter.itemCount - 1)
                }, 100)
            }
        }
    }

    private fun addNewMessage() {

        // Get data provided by user
        val messageText: String = editText_messageInput.text.toString()
        val currentDate = dateTimeInstance.format(java.util.Date())
        val newMessage = MessageEntity(messageText, currentDate, true)

        // Store the message
        viewModel.addMessage(newMessage)

        // Display the message
        messagesAdapter.addMessage(newMessage)

        // Clear inputs
        editText_messageInput.text?.clear()
        editText_messageInput.clearFocus()
    }
}
