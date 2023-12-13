package com.effectivemobile.testproject.task;

import com.effectivemobile.testproject.user.User;
import com.effectivemobile.testproject.validationgroup.Update;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
    @NotNull(groups = Update.class)
    private TaskStatus taskStatus = TaskStatus.CREATED;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    @NotNull(groups = Update.class)
    private TaskPriority taskPriority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id", referencedColumnName = "user_id")
    private User executor;
}
