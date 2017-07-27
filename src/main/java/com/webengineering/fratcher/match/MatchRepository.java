package com.webengineering.fratcher.match;

import com.webengineering.fratcher.comment.Comment;
import com.webengineering.fratcher.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends CrudRepository<Match, Long> {

    @Query("SELECT m FROM Match_ m WHERE :comment MEMBER OF m.comments")
    Match findMatchForComment(@Param("comment") Comment comment);
}
