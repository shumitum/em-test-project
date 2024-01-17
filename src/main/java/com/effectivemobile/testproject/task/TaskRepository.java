package com.effectivemobile.testproject.task;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("select t from Task as t " +
            "where (t.author.id = :authorId) " +
            "and ((:priority) is NULL or t.taskPriority = :priority)" +
            "order by t.id")
    List<Task> searchTasksByAuthorOrderById(@Param("authorId") Integer authorId,
                                            @Param("priority") TaskPriority priority,
                                            PageRequest page);

    @Query("select t from Task as t " +
            "where (t.executor.id = :executorId) " +
            "and ((:priority) is NULL or t.taskPriority = :priority)" +
            "order by t.id")
    List<Task> searchTasksByExecutorOrderById(@Param("executorId") Integer executorId,
                                              @Param("priority") TaskPriority priority,
                                              PageRequest page);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"author", "executor"})
    Optional<Task> findById(Integer taskId);
}