package com.org.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private Integer messageId;
    private Integer status;
    private String message;
    private String sender;
    private String subject;
    private LocalDateTime dateTime;

    public MessageDTO() {}

    public MessageDTO(Integer messageId, Integer status, String message, String sender, String subject, LocalDateTime dateTime) {
        this.messageId = messageId;
        this.status = status;
        this.message = message;
        this.sender = sender;
        this.subject = subject;
        this.dateTime = dateTime;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
