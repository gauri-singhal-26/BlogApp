package com.example.blogApp.repository;

import com.example.blogApp.dto.CommentDto;
import com.example.blogApp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select * from comment", nativeQuery = true)
    List<Comment> findLevelOneComments();
}
