package cz.uhk.pro2.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.uhk.pro2.models.chatFileOperations.ChatFileOperations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ToFileChatClient implements ChatClient{
    private String loggedUser;
    private List<Message> messages;
    private List<String> loggedUsers;

    private List<ActionListener> listenersLoggedUsersChanged = new ArrayList<>();
    private List<ActionListener> listenersUpdateMessages = new ArrayList<>();

    private static final String MESSAGES_FILE = "./messages.json";

    Gson gson;
    ChatFileOperations chatFileOperations;

    public ToFileChatClient(ChatFileOperations chatFileOperations){
        gson = new GsonBuilder().setPrettyPrinting().create();
        this.chatFileOperations = chatFileOperations;

        messages = chatFileOperations.loadMessages();
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
        chatFileOperations.writeLoggedUsersToFile(loggedUsers);
    }

    @Override
    public void logout() {
        loggedUsers.remove(loggedUser);
        addMessage(new Message(Message.USER_LOGGED_OUT,loggedUser));
        raisEventLoggedUsersChanged();
        loggedUser = null;
        chatFileOperations.writeLoggedUsersToFile(loggedUsers);
    }

    @Override
    public void sendMessage(String text) {
        addMessage(new Message(loggedUser,text));
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

    private void addMessage(Message message) {
        messages.add(message);
        chatFileOperations.writeMessagesToFile(messages);
        raisEventUpdateMessages();
    }
}
