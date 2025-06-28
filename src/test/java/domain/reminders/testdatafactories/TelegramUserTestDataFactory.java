package domain.reminders.testdatafactories;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class TelegramUserTestDataFactory {

    public static @NotNull Update getBasicUpdateInvited() {
        var chat = new Chat();
        chat.setId(123L);
        var user = new User();
        user.setUserName("test");
        var messageInvited = new Message();
        messageInvited.setText("/start");
        messageInvited.setChat(chat);
        messageInvited.setFrom(user);
        var updateInvited = new Update();
        updateInvited.setMessage(messageInvited);
        return updateInvited;
    }
}
