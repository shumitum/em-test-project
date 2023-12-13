package com.effectivemobile.testproject.comment;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByTaskIdOrderByCreated(Integer taskId, PageRequest page);
}