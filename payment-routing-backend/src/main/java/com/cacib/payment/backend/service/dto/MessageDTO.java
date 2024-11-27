package com.cacib.payment.backend.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Setter
@Getter
public class MessageDTO implements Serializable {


    private Long id;

    private String content;

    private String queueName;

    private LocalDateTime receivedAt;

    public MessageDTO() {}

    public MessageDTO(Long id, String content ,String queueName ,LocalDateTime receivedAt) {
        this.id = id;
        this.content = content;
        this.queueName = queueName;
        this.receivedAt = receivedAt;
    }

}
