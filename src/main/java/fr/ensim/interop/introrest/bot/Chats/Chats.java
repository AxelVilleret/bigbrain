package fr.ensim.interop.introrest.bot.Chats;

import java.util.ArrayList;
import java.util.HashMap;

import fr.ensim.interop.introrest.model.openai.Message;

public class Chats extends HashMap<Long, ArrayList<Message>> {
    public ArrayList<Message> addMessage(Long id, Message message) {
        ArrayList<Message> chat = this.get(id);
        if (chat == null) {
            chat = new ArrayList<>();
        }
        if (chat.size() >= 10) {
            chat.remove(0); // remove the oldest message
        }
        chat.add(message);
        this.put(id, chat);
        return chat;
    }
}
