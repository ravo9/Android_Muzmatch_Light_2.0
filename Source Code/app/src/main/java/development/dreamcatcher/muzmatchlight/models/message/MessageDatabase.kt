package development.dreamcatcher.muzmatchlight.models.message

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(MessageEntity::class), version = 1)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {

        private var instance: MessageDatabase? = null

        fun getInstance(context: Context): MessageDatabase? {

            if (instance == null) {

                synchronized(MessageDatabase::class) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            MessageDatabase::class.java, "messages_database")
                            .build()
                }
            }
            return instance
        }
    }
}