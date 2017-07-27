package com.webengineering.fratcher.match;

import com.webengineering.fratcher.comment.Comment;
import com.webengineering.fratcher.user.User;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "Match_")
public class Match {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private User firstUser;

    @ManyToOne(optional = false)
    private User secondUser;

    private boolean bothMatching;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public Match() {
        // Default constructor for JPA.
        comments = new LinkedList<>();
    }

    public Match(Long id, User firstUser, User secondUser, boolean bothMatching) {
        this.id = id;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.bothMatching = bothMatching;
        comments = new LinkedList<>();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    public boolean isBothMatching() {
        return bothMatching;
    }

    public void setBothMatching(boolean bothMatching) {
        this.bothMatching = bothMatching;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", firstUser=" + firstUser +
                ", secondUser=" + secondUser +
                ", bothMatching=" + bothMatching +
                '}';
    }
}
