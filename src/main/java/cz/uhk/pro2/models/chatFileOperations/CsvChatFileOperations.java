package cz.uhk.pro2.models.chatFileOperations;

import cz.uhk.pro2.models.Message;

import java.util.List;

public class CsvChatFileOperations implements  ChatFileOperations{
    @Override
    public List<Message> loadMessages() {
        return null;
    }

    @Override
    public void writeMessagesToFile(List<Message> messages) {

    }

    @Override
    public List<String> loadLoggedUsers() {
        return null;
    }

    @Override
    public void writeLoggedUsersToFile(List<String> messages) {

    }
}
