package com.webengineering.fratcher.text;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TextRepository extends CrudRepository<Text, Long> {
    @Query("SELECT t from Text t ORDER BY t.createdAt DESC")
    List<Text> findAll();
}
