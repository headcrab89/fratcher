package com.webengineering.fratcher.text;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.util.Date;

@Entity
public class Text {
    @Id
    @GeneratedValue
    private  Long id;

    private String userText;
    private Date createdAt;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method is called before an entity is persisted in the database. This is in contrast to our previous
     * approach where an object's createdAt depends on the date of its instantiation.
     * <p>
     * Information about @PrePersist where found by using the search terms "jpa annotations createdat".
     */
    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }
}
