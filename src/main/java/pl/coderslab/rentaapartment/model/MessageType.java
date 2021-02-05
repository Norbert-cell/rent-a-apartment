package pl.coderslab.rentaapartment.model;

public enum MessageType {

    FAULT,
    NORMAL;

    public String getMessageType(){
        return this.name();
    }
}
