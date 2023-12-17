package com.effectivemobile.testproject.comment;

import com.effectivemobile.testproject.comment.dto.CreateCommentDto;
import com.effectivemobile.testproject.comment.dto.UpdateCommentDto;
import com.effectivemobile.testproject.comment.dto.ViewCommentDto;
import com.effectivemobile.testproject.task.TaskMapper;
import com.effectivemobile.testproject.task.TaskService;
import com.effectivemobile.testproject.user.Role;
import com.effectivemobile.testproject.user.User;
import com.effectivemobile.testproject.user.UserPosition;
import com.effectivemobile.testproject.user.UserService;
import com.effectivemobile.testproject.user.dto.ViewUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @Mock
    private TaskService taskService;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private TaskMapper taskMapper;

    private ViewCommentDto viewCommentDto;

    private Comment comment;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(1)
                .role(Role.EXECUTOR)
                .userPosition(UserPosition.DEV_JUNIOR)
                .name("Junior")
                .build();

        LocalDateTime time = LocalDateTime.now();

        comment = Comment.builder()
                .id(1)
                .text("text")
                .author(user)
                .created(time)
                .build();

        viewCommentDto = ViewCommentDto.builder()
                .id(1)
                .text("text")
                .author(new ViewUserDto())
                .created(time)
                .build();
    }

    @Test
    void createComment_whenInvokeWithCorrectData_thenCreateNeComment() {
        when(commentMapper.toComment(any())).thenReturn(comment);
        when(commentMapper.toViewCommentDto(any())).thenReturn(viewCommentDto);

        ViewCommentDto actualComment = commentService.createComment(anyInt(), new CreateCommentDto());

        assertEquals(viewCommentDto, actualComment);
    }

    @Test
    void updateComment_whenInvokeWithCorrectData_whenUpdateComment() {
        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(comment));
        when(commentMapper.toViewCommentDto(any())).thenReturn(viewCommentDto);

        ViewCommentDto actualComment = commentService.updateComment(1, 1, new UpdateCommentDto());

        assertEquals(viewCommentDto, actualComment);
    }

    @Test
    void deleteComment_whenInvokeWithCorrectData_thenDoesNotThrowAnyException() {
        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(comment));

        assertDoesNotThrow(() -> commentService.deleteComment(1, 1));
    }

    @Test
    void deleteComment_whenInvokeWithInvalidCommentId_thenTrowNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> commentService.deleteComment(1, 1));
    }

    @Test
    void getCommentsByTaskId_whenInvokeWithCorrectTaskId_thenReturnListOfComments() {
        when(commentRepository.findCommentsByTaskIdOrderByCreated(anyInt(), any()))
                .thenReturn(List.of(comment));
        when(commentMapper.toViewCommentDto(any())).thenReturn(viewCommentDto);

        List<ViewCommentDto> comments = commentService.getCommentsByTaskId(1, 0, 10);

        assertEquals(1, comments.size());
        assertEquals(1, comments.get(0).getId());
    }
}