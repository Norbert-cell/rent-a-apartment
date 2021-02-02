package pl.coderslab.rentaapartment.model;

public enum MessageType {

    FAULT_MESSAGE,
    NORMAL_MESSAGE;

    public String getMessageType(){
        return this.name();
    }
}
