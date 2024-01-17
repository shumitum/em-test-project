package com.effectivemobile.testproject.task;

import com.effectivemobile.testproject.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks", schema = "public")
public class Task {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "header")
    private String header;

    @NotBlank
    @Column(name = "description")
    private String description;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull
    private TaskStatus taskStatus = TaskStatus.CREATED;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    @NotNull
    private TaskPriority taskPriority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id", referencedColumnName = "user_id")
    private User executor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id)
                && Objects.equals(header, task.header)
                && Objects.equals(description, task.description)
                && taskStatus == task.taskStatus
                && taskPriority == task.taskPriority
                && Objects.equals(author, task.author)
                && Objects.equals(executor, task.executor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, header, description, taskStatus, taskPriority, author, executor);
    }
}
