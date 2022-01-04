package cz.uhk.pro2.models.api;

import cz.uhk.pro2.models.Message;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class MessageResponse {
    private String author;
    private String text;
    private String created;

    public MessageResponse(String author, String text, String created) {
        this.author = author;
        this.text = text;
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public String getCreated() {
        return created;
    }
}
