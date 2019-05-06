package development.dreamcatcher.muzmatchlight.models.message

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData


class MessageRepository(application: Application) {

    private var messageDao: MessageDao

    private var allMessages: LiveData<List<MessageEntity>>

    private val database: MessageDatabase

    init {
        database = MessageDatabase.getInstance(application.applicationContext)!!
        messageDao = database.messageDao()
        allMessages = messageDao.getAllMessages()
    }

    fun getAllMessages(): LiveData<List<MessageEntity>> {
        return allMessages
    }

    fun addMessage(message: MessageEntity) {
        InsertMessageAsyncTask(messageDao).execute(message)
    }

    fun deleteAllMessages() {
        InsertMessageAsyncTask(messageDao).execute()
    }

    private class InsertMessageAsyncTask(val messageDao: MessageDao) : AsyncTask<MessageEntity, Unit, Unit>() {

        override fun doInBackground(vararg p0: MessageEntity?) {
            messageDao.insert(p0[0]!!)
        }
    }

    private class DeleteMessagesAsyncTask(val messageDao: MessageDao) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit) {
            messageDao.deleteAllMessages()
        }
    }
}