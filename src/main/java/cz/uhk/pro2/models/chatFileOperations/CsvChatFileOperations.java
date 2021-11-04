package cz.uhk.pro2.models.chatFileOperations;

import com.google.gson.Gson;
import com.sun.javafx.binding.StringFormatter;
import cz.uhk.pro2.models.Message;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvChatFileOperations implements  ChatFileOperations{
    private static final String MESSAGES_FILE = "./messages.csv";
    private static final String USERS_FILE = "./users.csv";

    @Override
    public List<Message> loadMessages() {
        List<String> fileContent = readFile(MESSAGES_FILE);
        List<Message> msgs = new ArrayList<>();

        for(String line : fileContent){
            String[] items = line.split(";");
            LocalDateTime time = LocalDateTime.parse(items[0]);
            Message msg = new Message(items[1],items[2]);
            msg.setCreated(time);
            msgs.add(msg);
        }
        return msgs;
    }

    @Override
    public void writeMessagesToFile(List<Message> messages) {
        try {
            FileWriter writer = new FileWriter(MESSAGES_FILE);
            for(Message msg : messages){
                String str = String.format("%s;%s;%s\n",msg.getCreated(),msg.getAuthor(),msg.getText());
                writer.write(str);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> loadLoggedUsers() {
        return readFile(USERS_FILE);
    }

    @Override
    public void writeLoggedUsersToFile(List<String> users) {
        try {
            FileWriter writer = new FileWriter(USERS_FILE);
            for(String user : users){
                writer.write(user+"\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readFile(String path){
        List<String> content = new ArrayList<>();
        try {
            FileReader reader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while((line = bufferedReader.readLine()) != null){
                content.add(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return content;
    }

}
