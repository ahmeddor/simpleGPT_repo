package com.example.simplegpt;

public class Message {
    // alter between me and bot
    public static String SENT_BY_ME = "me";
    public static String SENT_BY_BOT = "bot";

    String sentBy;
    String message;

    public Message( String message,String sentBy) {
        this.sentBy = sentBy;
        this.message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
