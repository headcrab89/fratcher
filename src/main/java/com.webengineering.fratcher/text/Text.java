package com.webengineering.fratcher.text;

import java.util.Date;

public class Text {
    private String userText;
    private Date createdAt;

    public Text(String userText) {
        this.userText = userText;
        createdAt = new Date();
    }

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
