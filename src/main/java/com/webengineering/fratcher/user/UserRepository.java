package com.webengineering.fratcher.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User_ u WHERE u.userName = :userName")
    User findByUserName(@Param("userName") String userName);

    @Query("SELECT u from User_ u WHERE u.userName = :userName AND u.password = :password")
    User findByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    @Modifying
    @Query("UPDATE User_ u SET u.lastActivity = :lastActivity WHERE u.id = :id ")
    @Transactional
    void updateLastActivity(@Param("lastActivity") Date lastActivity, @Param("id") Long id);
}