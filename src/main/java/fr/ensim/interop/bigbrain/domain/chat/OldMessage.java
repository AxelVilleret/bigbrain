package fr.ensim.interop.bigbrain.domain.chat;

public class OldMessage {
    private String content;
    private boolean isFromUser;

    public OldMessage(String content, boolean isFromUser) {
        this.content = content;
        this.isFromUser = isFromUser;
    }

    public String getContent() {
        return content;
    }

    public boolean isFromUser() {
        return isFromUser;
    }

    @Override
    public String toString() {
        return "OldMessage{" +
                "content='" + content + '\'' +
                ", isFromUser=" + isFromUser +
                '}';
    }
}
