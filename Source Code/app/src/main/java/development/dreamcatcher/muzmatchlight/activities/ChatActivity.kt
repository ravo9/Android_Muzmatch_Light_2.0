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
        recyclerView_messages.adapter           = messagesAdapter
        recyclerView_messages.layoutManager     = LinearLayoutManager(this)

        // Observe data provided by ViewModel
        viewModel.getAllMessages().observe(this, Observer<List<MessageEntity>> { messages ->
            messagesAdapter.setMessages(messages)

            // Upload fake messages as an initial input to mock another user's messages
            if (messages.isEmpty()) {
                viewModel.uploadFakeInitialMessages(dateTimeInstance)
            }
        })

        editText_messageInput.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                addNewMessage()
                // Keep focus on this input element.
                editText_messageInput.requestFocus()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun addNewMessage() {
        val messageText: String = editText_messageInput.text.toString()
        val currentDate = dateTimeInstance.format(java.util.Date())
        viewModel.addMessage(MessageEntity(messageText, currentDate, true))
        editText_messageInput.text?.clear()
        editText_messageInput.clearFocus()
    }
}
