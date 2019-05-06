package development.dreamcatcher.muzmatchlight.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import development.dreamcatcher.muzmatchlight.R
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
        val resources = getApplication<Application>().resources
        addMessage(MessageEntity(resources.getString(R.string.fakeMessage01), fakeDate, false))
        addMessage(MessageEntity(resources.getString(R.string.fakeMessage02), fakeDate, true))
        addMessage(MessageEntity(resources.getString(R.string.fakeMessage03), fakeDate, false))
        addMessage(MessageEntity(resources.getString(R.string.fakeMessage04), fakeDate, true))
        addMessage(MessageEntity(resources.getString(R.string.fakeMessage05), fakeDate, false))
        addMessage(MessageEntity(resources.getString(R.string.fakeMessage06), fakeDate, true))
        addMessage(MessageEntity(resources.getString(R.string.fakeMessage07), fakeDate, false))
        addMessage(MessageEntity(resources.getString(R.string.fakeMessage08), fakeDate, true))
    }
}