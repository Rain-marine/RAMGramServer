package models.trimmed;

import controllers.Controllers;
import controllers.MessageController;
import javafx.scene.control.Label;
import models.Message;
import models.types.MessageLink;
import models.types.MessageStatus;
import repository.Repositories;

public class TrimmedMessage implements Controllers, Repositories {

    private long messageId;
    private String messageText;
    private String messageDate;
    private byte[] imageArray;
    private byte[] profileImageArray;
    private MessageController.TYPE type;
    private String sender;
    private String grandSender;
    private MessageStatus status;
    private MessageLink link;

    public TrimmedMessage() {
    }

    public TrimmedMessage(long id , long loggedUserId) {
        this.messageId = id;
        this.messageText = MESSAGE_CONTROLLER.getMessageText(messageId);
        this.messageDate = MESSAGE_CONTROLLER.getMessageDate(messageId);
        this.imageArray = MESSAGE_CONTROLLER.getMessageImage(messageId);
        this.profileImageArray = MESSAGE_CONTROLLER.getSenderProfile(messageId);
        this.sender = MESSAGE_CONTROLLER.getMessageSender(messageId);
        this.grandSender = MESSAGE_CONTROLLER.getMessageGrandSender(messageId);
        this.type = MESSAGE_CONTROLLER.getMessageType(messageId , loggedUserId);
        this.link = MESSAGE_REPOSITORY.getById(messageId).getLink();
        this.status =MESSAGE_REPOSITORY.getById(messageId).getStatus();
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public byte[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(byte[] imageArray) {
        this.imageArray = imageArray;
    }

    public byte[] getProfileImageArray() {
        return profileImageArray;
    }

    public void setProfileImageArray(byte[] profileImageArray) {
        this.profileImageArray = profileImageArray;
    }

    public MessageController.TYPE getType() {
        return type;
    }

    public void setType(MessageController.TYPE type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGrandSender() {
        return grandSender;
    }

    public void setGrandSender(String grandSender) {
        this.grandSender = grandSender;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public MessageLink getLink() {
        return link;
    }

    public void setLink(MessageLink link) {
        this.link = link;
    }
}

