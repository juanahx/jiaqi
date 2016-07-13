package com.alberg.jiaqi.rest;

public enum MessageType {

    Text("text"),
    Image("image"),
    Music("music"),
    Video("video"),
    Voice("voice"),
    Location("location"),
    Link("link"),
    Event("event");
    
    private String messageType = "";

    MessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * @return the msgType
     */
    @Override
    public String toString() {
        return messageType;
    }
}
