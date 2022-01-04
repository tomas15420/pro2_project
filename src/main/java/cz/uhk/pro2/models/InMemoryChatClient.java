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
    private List<ActionListener> listenersUpdateMessages = new ArrayList<>();

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
        addMessage(new Message(Message.USER_LOGGED_IN,userName));
        raisEventLoggedUsersChanged();
        raisEventUpdateMessages();
    }

    @Override
    public void logout() {
        addMessage(new Message(Message.USER_LOGGED_OUT,loggedUser));
        loggedUsers.remove(loggedUser);
        loggedUser = null;
        raisEventLoggedUsersChanged();
        raisEventUpdateMessages();
    }

    @Override
    public void sendMessage(String text) {
        addMessage(new Message(loggedUser,text));
        raisEventUpdateMessages();
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

    @Override
    public void addActionListenerUpdateMessages(ActionListener toAdd) {
        listenersUpdateMessages.add(toAdd);
    }

    private void addMessage(Message message){
        messages.add(message);
        raisEventUpdateMessages();
    }

    private void raisEventLoggedUsersChanged(){
        for(ActionListener listener : listenersLoggedUsersChanged){
            listener.actionPerformed(new ActionEvent(this, 1, "listenersLoggedUsers"));
        }
    }

    public void raisEventUpdateMessages(){
        for(ActionListener listener : listenersUpdateMessages){
            listener.actionPerformed(new ActionEvent(this, 1, "listenersUpdateMessage"));
        }
    }
}
