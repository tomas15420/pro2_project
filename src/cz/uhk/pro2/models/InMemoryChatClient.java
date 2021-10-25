package cz.uhk.pro2.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class InMemoryChatClient implements ChatClient {
    private String loggedUser;
    private List<Message> messages;
    private List<String> loggedUsers;

    private List<ActionListener> listenersLoggedUsersChanged = new ArrayList<>();

    public InMemoryChatClient(){
        messages = new ArrayList<>();
        loggedUsers = new ArrayList<>();
    }

    @Override
    public boolean isAuthenticated() {
        return loggedUser != null;
    }

    @Override
    public void login(String userName) {
        loggedUser = userName;
        loggedUsers.add(loggedUser);
        messages.add(new Message(Message.USER_LOGGED_IN,userName));
        raisEventLoggedUsersChanged();
    }

    @Override
    public void logout() {
        loggedUsers.remove(loggedUser);
        loggedUser = null;
        messages.add(new Message(Message.USER_LOGGED_OUT,loggedUser));
        raisEventLoggedUsersChanged();
    }

    @Override
    public void sendMessage(String text) {
        messages.add(new Message(loggedUser,text));
    }

    @Override
    public List<String> getLoggedUsers() {
        return loggedUsers;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public void addActionListenerLoggedUsersChanged(ActionListener toAdd) {
        listenersLoggedUsersChanged.add(toAdd);
    }

    private void raisEventLoggedUsersChanged(){
        for(ActionListener listener : listenersLoggedUsersChanged){
            listener.actionPerformed(new ActionEvent(this, 1, "listenersLoggedUsers"));
        }
    }
}
