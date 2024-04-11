package edu.communication.hemo.patient.model;

public class ChatDetails {
    private String imageURL;
    private String message;
    private String messageType;
    private String receiver;
    private String receiverMail;
    private String sender;
    private String senderMail;

    public ChatDetails(String sender, String receiver, String message, String senderMail, String receiverMail, String messageType, String imageURL) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.senderMail = senderMail;
        this.receiverMail = receiverMail;
        this.messageType = messageType;
        this.imageURL = imageURL;
    }

    public ChatDetails() {
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderMail() {
        return this.senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public String getReceiverMail() {
        return this.receiverMail;
    }

    public void setReceiverMail(String receiverMail) {
        this.receiverMail = receiverMail;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
