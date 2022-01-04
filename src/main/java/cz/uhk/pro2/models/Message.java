package cz.uhk.pro2.models;

import cz.uhk.pro2.models.api.MessageResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class Message {
    private String author;
    private String text;
    private LocalDateTime created;

    public static final int USER_LOGGED_IN = 1;
    public static final int USER_LOGGED_OUT = 2;

    private static final String AUTHOR_SYSTEM = "System";

    public Message(ResultSet resultSet) throws SQLException {
        author = resultSet.getString("author");
        text = resultSet.getString("text");
        created = resultSet.getTimestamp("created").toLocalDateTime();
    }

    public Message(MessageResponse messageResponse) {
        author = messageResponse.getAuthor();
        text = messageResponse.getText();
        created = ZonedDateTime.parse(messageResponse.getCreated()).toLocalDateTime();
    }

    public Message(String author, String text) {
        this.author = author;
        this.text = text;
        created = LocalDateTime.now();
    }

    public Message(int type, String userName){
        switch(type){
            case USER_LOGGED_IN:{
                text = userName + " has entered the chat";
                break;
            }
            case USER_LOGGED_OUT:{
                text = userName + " has left the chat";
                break;
            }
        }
        created = LocalDateTime.now();
        author = AUTHOR_SYSTEM;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s",created.format(DateTimeFormatter.ofPattern("HH:mm:ss")),author,text);
    }
}
