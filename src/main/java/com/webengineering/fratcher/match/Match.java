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
    private User initUser;

    @ManyToOne(optional = false)
    private User matchUser;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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

    public User getInitUser() {
        return initUser;
    }

    public void setInitUser(User initUser) {
        this.initUser = initUser;
    }

    public User getMatchUser() {
        return matchUser;
    }

    public void setMatchUser(User matchUser) {
        this.matchUser = matchUser;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    @PrePersist
    public void prePersist() {
        comments = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", initUser=" + initUser +
                ", matchUser=" + matchUser +
                ", matchStatus=" + matchStatus +
                '}';
    }
}
