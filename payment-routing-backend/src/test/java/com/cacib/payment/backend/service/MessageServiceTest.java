package com.cacib.payment.backend.service;

import com.cacib.payment.backend.model.Message;
import com.cacib.payment.backend.repository.MessageRepository;
import com.cacib.payment.backend.service.dto.MessageDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    @Test
    public void testReceiveMessage() {
        String testContent = "Test Message";

        String savedMessage = messageService.receiveMessage(testContent);

        assertNotNull(savedMessage);
        assertEquals(testContent, savedMessage);

    }

    @Test
    public void testGetAllMessages() {
        Page<MessageDTO> messages = messageService.getAllMessages(PageRequest.of(0, 10));
        assertNotNull(messages);
    }
}