package com.webengineering.fratcher.comment;

import com.webengineering.fratcher.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {
    public static final int COMMENT_LENGTH = 65536;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private User author;

    @Column(length = Comment.COMMENT_LENGTH)
    private String text;
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", author=" + author +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
