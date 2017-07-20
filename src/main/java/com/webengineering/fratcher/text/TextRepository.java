package com.webengineering.fratcher.text;

import com.webengineering.fratcher.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TextRepository extends CrudRepository<Text, Long> {
    @Query("SELECT t from Text t ORDER BY t.createdAt DESC")
    List<Text> findAll();

    @Query("SELECT t from Text t WHERE t.author = :user")
    Text findByUser(@Param("user") User user);
}
