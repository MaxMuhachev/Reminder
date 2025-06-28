package domain.reminders.testdatafactories;

import domain.reminders.dao.models.User;

public class UserTestDataFactory {

    public static User getBasicUser() {
        return new User().setId("123").setName("name").setEmail("mail@mail.ru");
    }
}
