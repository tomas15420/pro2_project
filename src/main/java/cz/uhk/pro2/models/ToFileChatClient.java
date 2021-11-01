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

        messages = new ArrayList<>();
        loggedUsers = new ArrayList<>();

        this.chatFileOperations = chatFileOperations;

        readMessagesFromFile();
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
    }

    @Override
    public void logout() {
        loggedUsers.remove(loggedUser);
        loggedUser = null;
        addMessage(new Message(Message.USER_LOGGED_OUT,loggedUser));
        raisEventLoggedUsersChanged();
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

    private void addMessage(Message message){
        messages.add(message);
        writeMessagesToFile();
        raisEventUpdateMessages();
    }

    private void writeMessagesToFile(){
        String jsonText = gson.toJson(messages);

        try {
            FileWriter writer = new FileWriter(MESSAGES_FILE);
            writer.write(jsonText);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessagesFromFile(){
        try {
            FileReader reader = new FileReader(MESSAGES_FILE);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder jsonText = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                jsonText.append(line);
            }
            Type targetType = new TypeToken<ArrayList<Message>>(){}.getType();
            messages = gson.fromJson(jsonText.toString(), targetType);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
