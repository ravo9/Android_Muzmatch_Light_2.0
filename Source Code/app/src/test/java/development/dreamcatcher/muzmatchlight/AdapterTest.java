package development.dreamcatcher.muzmatchlight;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.text.SimpleDateFormat;
import java.util.Date;

import development.dreamcatcher.muzmatchlight.adapters.MessageAdapter;
import development.dreamcatcher.muzmatchlight.models.message.MessageEntity;


public class AdapterTest {

    private MessageAdapter messageAdapter;

    @Before
    public void setupChatViewModel() {

        // Initialize adapter
        messageAdapter = new MessageAdapter();
    }

    @Test
    public void getItemCountFromEmptyMessageList() {

        // Perform the action
        int messagesAmount = messageAdapter.getItemCount();

        // Check if repository behaves as expected
        Assert.assertEquals(0, messagesAmount);
    }

    @Test
    public void addNewMessage() {

        // Create fake message object
        Date date = new Date(49, 3, 30, 22, 30);
        String dateAsString = SimpleDateFormat.getDateTimeInstance().format(date);
        MessageEntity message = new MessageEntity("Hi ;)", dateAsString, false);

        // Add the message
        messageAdapter.addMessage(message);

        // Check if message has been added
        int messagesAmount = messageAdapter.getItemCount();
        Assert.assertEquals(1, messagesAmount);
    }
}