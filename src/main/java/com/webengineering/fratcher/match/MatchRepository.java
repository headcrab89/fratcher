package com.webengineering.fratcher.match;

import com.webengineering.fratcher.comment.Comment;
import com.webengineering.fratcher.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends CrudRepository<Match, Long> {

//    @Query("SELECT new Match_ (m.id, m.initUser, m.matchUser, m.bothMatching) FROM Match_ m " +
//            "WHERE m.initUser.id = :userId OR (m.matchUser.id = :userId AND m.bothMatching = true)")
//    Iterable<Match> findByUserId(@Param("userId") Long userId);

    @Query("SELECT m FROM Match_ m WHERE m.initUser = :matchUser AND m.matchUser = :initUser AND m.matchStatus='LIKE'")
    Match findOtherMatch(@Param("initUser") User initUser, @Param("matchUser") User matchUser);

    @Query("SELECT m FROM Match_ m WHERE :comment MEMBER OF m.comments")
    Match findMatchForComment(@Param("comment") Comment comment);
}
