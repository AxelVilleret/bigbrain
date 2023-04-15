package fr.ensim.interop.introrest.model.telegram.perso;

public class UserMessage {

    private Long chat_id;
    private String text;

    public Long getChat_id() {
        return chat_id;
    }

    public void setChat_id(Long chat_id) {
        this.chat_id = chat_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String content) {
        this.text = content;
    }

}
