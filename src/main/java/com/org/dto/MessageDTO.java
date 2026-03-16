package com.org.dto;

public class MessageDTO {
    private Integer message_id;
    private Integer status;
    private String message;
    private String sender;
    private String subject;
    private String date_time;

    public MessageDTO() {
    }

    public MessageDTO(Integer message_id, Integer status, String message, String sender, String subject, String date_time) {
        this.message_id = message_id;
        this.status = status;
        this.message = message;
        this.sender = sender;
        this.subject = subject;
        this.date_time = date_time;
    }

    public Integer getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
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
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
