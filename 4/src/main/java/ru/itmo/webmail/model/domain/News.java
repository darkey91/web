package ru.itmo.webmail.model.domain;

import java.io.Serializable;

public class News implements Serializable {
    private long userId;
    private String text;
    private String userName;

    public News(long userId, String text, String userName) {
        this.userId = userId;
        this.text = text;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
