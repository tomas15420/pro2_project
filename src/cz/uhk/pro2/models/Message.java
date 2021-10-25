package cz.uhk.pro2.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String author;
    private String text;
    private LocalDateTime created;

    public static final int USER_LOGGED_IN = 1;
    public static final int USER_LOGGED_OUT = 2;

    private final String AUTHOR_SYSTEM = "System";

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
        if(author == AUTHOR_SYSTEM){

        }

        return String.format("[%s] %s: %s",created.format(DateTimeFormatter.ofPattern("HH:mm:ss")),author,text);
    }
}
