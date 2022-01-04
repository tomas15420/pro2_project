package cz.uhk.pro2;

import cz.uhk.pro2.gui.MainFrame;
import cz.uhk.pro2.models.*;
import cz.uhk.pro2.models.chatFileOperations.ChatFileOperations;
import cz.uhk.pro2.models.chatFileOperations.CsvChatFileOperations;
import cz.uhk.pro2.models.chatFileOperations.JsonChatFileOperations;
import cz.uhk.pro2.models.database.DatabaseOperations;
import cz.uhk.pro2.models.database.DbInitializer;
import cz.uhk.pro2.models.database.JdbcDatabaseOperations;

public class Main {

    public static void main(String[] args) {
        String databaseDriver = "org.apache.derby.jdbc.EmbeddedDriver";
        String databaseUrl = "jdbc:derby:ChatClientDb";

        try{
            //ChatFileOperations chatFileOperations = new JsonChatFileOperations();
            ChatClient chatClient = new WebChatClient();

            DbInitializer dbInitializer = new DbInitializer(databaseDriver,databaseUrl);
            //dbInitializer.init();

            DatabaseOperations databaseOperations = new JdbcDatabaseOperations(databaseDriver,databaseUrl);
            chatClient = new DatabaseChatClient(databaseOperations);

            MainFrame mainFrame = new MainFrame(800, 600, chatClient);
            mainFrame.setVisible(true);
        }catch (Exception e){
            e.printStackTrace();
        }
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
