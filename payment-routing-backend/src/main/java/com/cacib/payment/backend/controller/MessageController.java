package com.cacib.payment.backend.controller;


import com.cacib.payment.backend.model.Message;
import com.cacib.payment.backend.service.MessageService;
import com.cacib.payment.backend.service.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;


@EnableJms
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private JmsTemplate jmsTemplate;


    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/rec")
    public String recv(){
        try{
            var str = jmsTemplate.receiveAndConvert("DEV.QUEUE.1").toString();
            log.info("Message received and saved: {}", str);
            return messageService.receiveMessage(str);
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }

    @GetMapping
    public Page<MessageDTO> getAllMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return messageService.getAllMessages(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
