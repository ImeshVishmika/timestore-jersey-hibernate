package com.org.dto;

public class MessageDTO {
    private Integer messageId;
    private Integer status;
    private String message;
    private String sender;
    private String subject;
    private String dateTime;

    public MessageDTO() {
    }

    public MessageDTO(Integer messageId, Integer status, String message, String sender, String subject, String dateTime) {
        this.messageId = messageId;
        this.status = status;
        this.message = message;
        this.sender = sender;
        this.subject = subject;
        this.dateTime = dateTime;
    }

    public Integer getMessage_id() {
        return messageId;
    }

    public void setMessage_id(Integer messageId) {
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

    public String getDate_time() {
        return dateTime;
    }

    public void setDate_time(String dateTime) {
        this.dateTime = dateTime;
    }
}
