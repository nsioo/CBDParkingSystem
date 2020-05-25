package com.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * 消息实体类
 */
@Entity
@Table(name = "t_message")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class MessageEntity implements Serializable {
    /**消息id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_messageId")
    private int messageId;
    /**消息内容*/
    @Column(name = "m_info")
    private String messageInfo;
    /**消息类型 0是个人消息，1是系统消息*/
    @Column(name = "m_type")
    private int messageType;
    /**消息发送日期*/
    @Column(name = "m_date")
    private Date messageDate = new Date(System.currentTimeMillis());
    /**消息状态 0表示未读，1表示已读，2表示已删除*/
    @Column(name = "m_status")
    private int messageStatus = 0;
    /**接收用户*/
    @OneToOne
    @JoinColumn(name = "fk_receiverId")
    private PublicUserEntity receiver;
    /**发送用户*/
    @OneToOne
    @JoinColumn(name = "fk_senderId")
    private PublicUserEntity sender;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public MessageEntity() {
    }

    public MessageEntity(String messageInfo, int messageType, Date messageDate, int messageStatus, PublicUserEntity receiver, PublicUserEntity sender) {
        this.messageInfo = messageInfo;
        this.messageType = messageType;
        this.messageDate = messageDate;
        this.messageStatus = messageStatus;
        this.receiver = receiver;
        this.sender = sender;
    }

    public MessageEntity(String messageInfo, int messageType, PublicUserEntity receiver, PublicUserEntity sender) {
        this.messageInfo = messageInfo;
        this.messageType = messageType;
        this.receiver = receiver;
        this.sender = sender;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessageDate() {
        return sdf.format(messageDate);
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }

    public PublicUserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(PublicUserEntity receiver) {
        this.receiver = receiver;
    }

    public PublicUserEntity getSender() {
        return sender;
    }

    public void setSender(PublicUserEntity sender) {
        this.sender = sender;
    }
}
