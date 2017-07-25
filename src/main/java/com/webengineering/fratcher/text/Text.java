package com.webengineering.fratcher.text;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webengineering.fratcher.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Text {
    public static final int TEXT_LENGTH = 65536;

    @Id
    @JsonIgnore
    @GeneratedValue
    private  Long id;

    @OneToOne(optional = false)
    private User author;

    @Column(length = Text.TEXT_LENGTH)
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

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Text text = (Text) o;

        return id != null ? id.equals(text.id) : text.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
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

    @Override
    public String toString() {
        return "Text{" +
                "id=" + id +
                ", author=" + author +
                ", userText='" + userText + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
