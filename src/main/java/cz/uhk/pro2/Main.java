package cz.uhk.pro2;

import cz.uhk.pro2.gui.MainFrame;
import cz.uhk.pro2.models.ChatClient;
import cz.uhk.pro2.models.InMemoryChatClient;
import cz.uhk.pro2.models.Message;
import cz.uhk.pro2.models.ToFileChatClient;
import cz.uhk.pro2.models.chatFileOperations.ChatFileOperations;
import cz.uhk.pro2.models.chatFileOperations.CsvChatFileOperations;
import cz.uhk.pro2.models.chatFileOperations.JsonChatFileOperations;

public class Main {

    public static void main(String[] args) {
        ChatFileOperations chatFileOperations = new JsonChatFileOperations();
        ChatClient chatClient = new ToFileChatClient(chatFileOperations);

        MainFrame mainFrame = new MainFrame(800, 600, chatClient);
        mainFrame.setVisible(true);
    }

    private static void testChat(){
        ChatClient chatClient = new InMemoryChatClient();
        chatClient.login("Tomáš");
        System.out.println("User is logged: "+chatClient.isAuthenticated());
        System.out.println("currently logged users");
        for (String user: chatClient.getLoggedUsers()) {
            System.out.println(user);
        }
        System.out.println("sending msg 1");
        chatClient.sendMessage("Hello World");
        System.out.println("sending msg 2");
        chatClient.sendMessage("Hello World again");
        for(Message message : chatClient.getMessages()){
            System.out.println(message);
        }
        System.out.println("loging out");
        chatClient.logout();
        System.out.println("User is logged: "+chatClient.isAuthenticated());

    }
}
