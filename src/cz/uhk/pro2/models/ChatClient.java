package cz.uhk.pro2.models;

import java.util.List;

public interface ChatClient {
    boolean isAuthenticated();
    void login(String userName);
    void logout();
    void sendMessage(String text);
    List<String> getLoggedUsers();
    List<Message> getMessages();
}
