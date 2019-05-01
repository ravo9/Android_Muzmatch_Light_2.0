package development.dreamcatcher.muzmatchlight.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import development.dreamcatcher.muzmatchlight.models.message.MessageEntity
import development.dreamcatcher.muzmatchlight.models.message.MessageRepository
import java.text.DateFormat


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
        val currentDate = dateTimeInstance.format(java.util.Date())
        addMessage(MessageEntity("Hi ;)", currentDate, false))
        addMessage(MessageEntity("Hello :)", currentDate, true))
        addMessage(MessageEntity("How are you today...? ;)", currentDate, false))
        addMessage(MessageEntity("Not bad :P How about yourself?", currentDate, true))
    }
}