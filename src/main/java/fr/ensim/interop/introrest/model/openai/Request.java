package fr.ensim.interop.introrest.model.openai;

import java.util.ArrayList;

public class Request {
    private String model;
    private ArrayList<Message> messages;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> prompt) {
        this.messages = prompt;
    }

}
