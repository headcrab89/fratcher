package com.webengineering.fratcher.match;

import com.webengineering.fratcher.comment.Comment;
import com.webengineering.fratcher.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends CrudRepository<Match, Long> {

    @Query("SELECT new Match_ (m.id, m.firstUser, m.secondUser, m.bothMatching) FROM Match_ m " +
            "WHERE m.firstUser.id = :userId OR (m.secondUser.id = :userId AND m.bothMatching = true)")
    Iterable<Match> findByUserId(@Param("userId") Long userId);

    @Query("SELECT m FROM Match_ m WHERE (m.firstUser = :firstUser " +
            "AND m.secondUser = :secondUser) OR (m.firstUser = :secondUser AND m.secondUser = :firstUser)")
    Match findMatchForUsers(@Param("firstUser") User firstUser, @Param("secondUser") User secondUser);

    @Query("SELECT m FROM Match_ m WHERE :comment MEMBER OF m.comments")
    Match findMatchForComment(@Param("comment") Comment comment);
}
