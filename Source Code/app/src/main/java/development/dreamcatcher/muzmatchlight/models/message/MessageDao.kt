package development.dreamcatcher.muzmatchlight.models.message

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MessageDao {

    @Insert
    fun insert(messageEntity: MessageEntity)

    @Query("SELECT * FROM messages_table")
    fun getAllMessages(): LiveData<List<MessageEntity>>

    @Delete
    fun deleteMessage(messageEntity: MessageEntity)

    @Query("DELETE FROM messages_table")
    fun deleteAllMessages()
}