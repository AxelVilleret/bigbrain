package fr.ensim.interop.bigbrain.infrastructure.chat.model;

import java.util.ArrayList;

public class Request {
    private String model;
    private ArrayList<Message> messages;

    public Request(String model, ArrayList<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Request [messages=" + messages + ", model=" + model + "]";
    }

}
