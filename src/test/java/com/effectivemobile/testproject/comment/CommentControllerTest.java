package com.effectivemobile.testproject.comment;

import com.effectivemobile.testproject.comment.dto.CreateCommentDto;
import com.effectivemobile.testproject.comment.dto.UpdateCommentDto;
import com.effectivemobile.testproject.comment.dto.ViewCommentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@WithUserDetails("mail@mail.com")
@AutoConfigureMockMvc(addFilters = false)
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateCommentDto comment;
    private UpdateCommentDto updComment;


    @BeforeEach
    void setUp() {
        comment = CreateCommentDto.builder()
                .text("Текст комментария")
                .taskId(1)
                .build();

        updComment = UpdateCommentDto.builder()
                .text("text")
                .build();
    }

    @Test
    @SneakyThrows
    void createComment_whenCommentIsValid_thenReturnedOkStatusWithComment() {
        when(commentService.createComment(1, comment)).thenReturn(new ViewCommentDto());

        String content = mockMvc.perform(post("/comments")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(commentService, times(1)).createComment(anyInt(), any());
        assertEquals(objectMapper.writeValueAsString(new ViewCommentDto()), content);
    }

    @Test
    @SneakyThrows
    void createComment_whenCommentIsNotValid_thenReturnedError() {
        mockMvc.perform(post("/comments")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new CreateCommentDto())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void updateComment_whenUpdCommentIsValid_thenReturnedOkStatusWithComment() {
        when(commentService.updateComment(1, 1, updComment)).thenReturn(new ViewCommentDto());

        String content = mockMvc.perform(patch("/comments/{commentId}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updComment)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(commentService, times(1)).updateComment(anyInt(), anyInt(), any());
        assertEquals(objectMapper.writeValueAsString(new ViewCommentDto()), content);
    }

    @Test
    @SneakyThrows
    void updateComment_whenUpdCommentIsNotValid_thenReturnedBadRequestStatus() {
        mockMvc.perform(patch("/comments/{commentId}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new UpdateCommentDto())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void deleteComment_whenCalledWithValidId_thenReturnedNoContent() {
        mockMvc.perform(delete("/comments/{commentId}", 1))
                .andExpect(status().isNoContent());

        verify(commentService, times(1)).deleteComment(anyInt(), anyInt());
    }

    @Test
    @SneakyThrows
    void getCommentsByTaskId_whenInvoke_thenReturnOkStatusWithListOfComments() {
        List<ViewCommentDto> comments = List.of(new ViewCommentDto());
        when(commentService.getCommentsByTaskId(1, 0, 10))
                .thenReturn(comments);

        String content = mockMvc.perform(get("/comments")
                        .param("taskId", "1")
                        .param("from", "0")
                        .param("size", "10")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(commentService, times(1)).getCommentsByTaskId(anyInt(), anyInt(), anyInt());
        assertEquals(objectMapper.writeValueAsString(comments), content);
    }
}