package com.webengineering.fratcher.match;

import com.webengineering.fratcher.comment.Comment;
import com.webengineering.fratcher.user.User;

import javax.persistence.*;
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

    @OneToMany
    private List<Comment> comments;


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
