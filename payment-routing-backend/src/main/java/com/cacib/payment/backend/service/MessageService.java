package com.cacib.payment.backend.service;

import com.cacib.payment.backend.model.Message;
import com.cacib.payment.backend.repository.MessageRepository;
import com.cacib.payment.backend.service.dto.MessageDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class MessageService {

    private final Logger log = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageRepository messageRepository;


    //@JmsListener(destination = "DEV.QUEUE.1", containerFactory = "myFactory")
    public String receiveMessage(String content) {
        try {
            Message message = new Message();
            message.setContent(content);
            message.setQueueName("DEV.QUEUE.1");
            message.setReceivedAt(LocalDateTime.now());
            messageRepository.save(message);
            log.info("Message received and saved: {}", content);
        } catch (Exception e) {
            log.error("Erreur lors de la réception du message", e);
        }
        return content;
    }

    public Page<MessageDTO> getAllMessages(Pageable pageable) {
        return messageRepository.findAll(pageable)
                .map(this::convertToDTO);
    }


    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    private MessageDTO convertToDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setQueueName(message.getQueueName());
        dto.setReceivedAt(message.getReceivedAt());
        return dto;
    }
}
