package com.webengineering.fratcher.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User_ u WHERE u.userName = :userName")
    User findByUserName(@Param("userName") String userName);

    @Query("SELECT u from User_ u WHERE u.userName = :userName AND u.password = :password")
    User findByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);
}