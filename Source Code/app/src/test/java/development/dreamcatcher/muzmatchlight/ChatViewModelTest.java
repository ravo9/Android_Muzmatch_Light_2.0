package development.dreamcatcher.muzmatchlight;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import androidx.lifecycle.LiveData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import development.dreamcatcher.muzmatchlight.models.message.MessageEntity;
import development.dreamcatcher.muzmatchlight.models.message.MessageRepository;
import development.dreamcatcher.muzmatchlight.viewmodels.ChatViewModel;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


public class ChatViewModelTest {

    private ChatViewModel chatViewModel;

    @Before
    public void setupChatViewModel() {

        MockitoAnnotations.initMocks(this);

        Application app = Mockito.mock(Application.class);
        Context context = Mockito.mock(Context.class);

        when(app.getApplicationContext()).thenReturn(context);
        when(context.getApplicationContext()).thenReturn(context);

        chatViewModel = new ChatViewModel(app);
    }

    @Test
    public void getMessagesListLiveDataReferenceFromInitializedRepository() {

        // Perform the action
        LiveData<List<MessageEntity>> messages = chatViewModel.getAllMessages();

        // Check if repository behaves as expected
        Assert.assertNotNull(messages);
    }
}