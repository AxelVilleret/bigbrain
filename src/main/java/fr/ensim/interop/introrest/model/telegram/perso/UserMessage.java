package fr.ensim.interop.introrest.model.telegram.perso;

public class UserMessage {

    private Long chat_id;
    private Integer reply_to_message_id;

    public Integer getReply_to_message_id() {
        return reply_to_message_id;
    }

    public void setReply_to_message_id(Integer reply_to_message_id) {
        this.reply_to_message_id = reply_to_message_id;
    }

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
