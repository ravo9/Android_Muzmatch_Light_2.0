package development.dreamcatcher.muzmatchlight.models.message

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages_table")
data class MessageEntity(
        @ColumnInfo(name = "message_text") val messageText: String,
        @ColumnInfo(name = "timestamp") val timestamp: String,
        @ColumnInfo(name = "is_own_message") val isOwnMessage: Boolean) {

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
}