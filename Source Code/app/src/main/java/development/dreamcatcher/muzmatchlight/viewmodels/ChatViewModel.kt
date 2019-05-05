package development.dreamcatcher.muzmatchlight.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import development.dreamcatcher.muzmatchlight.models.message.MessageEntity
import development.dreamcatcher.muzmatchlight.models.message.MessageRepository
import java.text.DateFormat
import java.util.*


class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private var messageRepository: MessageRepository = MessageRepository(application)

    private var allMessages: LiveData<List<MessageEntity>> = messageRepository.getAllMessages()

    fun addMessage(message: MessageEntity) {
        messageRepository.addMessage(message)
    }

    fun getAllMessages(): LiveData<List<MessageEntity>> {
        return allMessages
    }

    fun uploadFakeInitialMessages(dateTimeInstance: DateFormat) {
        val date = Date(49, 3, 30, 22, 30)
        val fakeDate = dateTimeInstance.format(date)
        addMessage(MessageEntity("Hi ;)", fakeDate, false))
        addMessage(MessageEntity("Hello :)", fakeDate, true))
        addMessage(MessageEntity("How are you today...? ;)", fakeDate, false))
        addMessage(MessageEntity("Not bad :P How about yourself?", fakeDate, true))
    }
}