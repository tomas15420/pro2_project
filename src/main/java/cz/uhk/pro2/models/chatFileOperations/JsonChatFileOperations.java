package cz.uhk.pro2.models.chatFileOperations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.uhk.pro2.models.Message;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonChatFileOperations implements ChatFileOperations{
    private static final String MESSAGES_FILE = "./messages.json";
    private static final String USERS_FILE = "./users.json";

    private Gson gson;

    public JsonChatFileOperations(){
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public List<Message> loadMessages(){
        ArrayList<Message> msgs = new ArrayList<>();
        StringBuilder jsonText = readFile(MESSAGES_FILE);
        if(jsonText != null){
            Type targetType = new TypeToken<ArrayList<Message>>(){}.getType();
            msgs = gson.fromJson(jsonText.toString(), targetType);
        }
        return msgs;
    }

    private StringBuilder readFile(String path){
        try {
            StringBuilder content = new StringBuilder();
            FileReader reader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while((line = bufferedReader.readLine()) != null){
                content.append(line);
            }
            return content;

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private void writeListToFile(String path, List<?> list){
        String content = gson.toJson(list);

        try {
            FileWriter writer = new FileWriter(path);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void writeMessagesToFile(List<Message> messages) {
        writeListToFile(MESSAGES_FILE,messages);
    }

    @Override
    public List<String> loadLoggedUsers() {
        ArrayList<String> usrs = new ArrayList<>();
        StringBuilder jsonText = readFile(USERS_FILE);
        if(jsonText != null){
            Type targetType = new TypeToken<ArrayList<String>>(){}.getType();
            usrs = gson.fromJson(jsonText.toString(), targetType);
        }
        return usrs;
    }

    @Override
    public void writeLoggedUsersToFile(List<String> users){
        writeListToFile(USERS_FILE,users);
    }
}
