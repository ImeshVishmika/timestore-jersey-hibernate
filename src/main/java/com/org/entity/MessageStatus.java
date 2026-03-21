package com.org.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "msg_status")
public class MessageStatus {

    @Id
    @Column(name = "msg_status_id", nullable = false)
    private Integer msgStatusId;

    @Column(name = "msg_status", length = 15)
    private String msgStatus;
}
