package cz.uhk.pro2.models.database;

import cz.uhk.pro2.models.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDatabaseOperations implements DatabaseOperations{
    private final Connection connection;
    private Statement statement;

    public JdbcDatabaseOperations(String driver, String url) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        connection = DriverManager.getConnection(url);
    }

    @Override
    public void addMessage(Message message) {
        try{
            statement = connection.createStatement();

            String sqlInsert = "INSERT INTO ChatMessages (author, text, created) VALUES " +
                    "('"+message.getAuthor()+"','"+message.getText()+"','"+ Timestamp.valueOf(message.getCreated())+"')";
            statement.executeUpdate(sqlInsert);

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        try{
            statement = connection.createStatement();

            String sqlSelect = "SELECT * FROM ChatMessages";
            ResultSet resultSet = statement.executeQuery(sqlSelect);
            while (resultSet.next()){
                messages.add(new Message(resultSet));
            }

            statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return messages;
    }
}
