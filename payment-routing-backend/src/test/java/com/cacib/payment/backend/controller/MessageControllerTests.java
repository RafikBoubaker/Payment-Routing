package com.cacib.payment.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.cacib.payment.backend.model.Message;
import com.cacib.payment.backend.service.MessageService;
import com.cacib.payment.backend.service.dto.MessageDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.jms.core.JmsTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTests {

    @Mock
    private MessageService messageService;

    @Mock
    private JmsTemplate jmsTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReceiveMessage() throws Exception {
        String messageContent = "Test Message";

        when(jmsTemplate.receiveAndConvert("DEV.QUEUE.1")).thenReturn(messageContent);
        when(messageService.receiveMessage(messageContent)).thenReturn(messageContent);

        mockMvc.perform(get("/api/messages/rec"))
                .andExpect(status().isOk())
                .andExpect(content().string(messageContent))
                .andDo(print());
    }

    @Test
    void shouldReturnAllMessages() throws Exception {
        List<MessageDTO> messages = Arrays.asList(
                new MessageDTO(1L, "Message 1", "DEV.QUEUE.1", LocalDateTime.now()),
                new MessageDTO(2L, "Message 2", "DEV.QUEUE.1", LocalDateTime.now())
        );
        Page<MessageDTO> messagePage = new PageImpl<>(messages, PageRequest.of(0, 10), messages.size());

        when(messageService.getAllMessages(any())).thenReturn(messagePage);

        mockMvc.perform(get("/api/messages")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andDo(print());
    }

    @Test
    void shouldReturnMessageById() throws Exception {
        Long messageId = 1L;
        Message message = new Message();
        message.setId(messageId);
        message.setContent("Test Message");
        message.setQueueName("DEV.QUEUE.1");
        message.setReceivedAt(LocalDateTime.now());

        when(messageService.getMessageById(messageId)).thenReturn(Optional.of(message));

        mockMvc.perform(get("/api/messages/{id}", messageId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(messageId))
                .andExpect(jsonPath("$.content").value("Test Message"))
                .andDo(print());
    }

    @Test
    void shouldReturnNotFoundWhenMessageIdDoesNotExist() throws Exception {
        Long messageId = 999L;

        when(messageService.getMessageById(messageId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/messages/{id}", messageId))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}