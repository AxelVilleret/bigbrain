package fr.ensim.interop.bigbrain.domain.chat;

public class MessageWithAnswer {
    private OldMessage message;
    private OldMessage answer;

    public MessageWithAnswer(OldMessage message, OldMessage answer) {
        this.message = message;
        this.answer = answer;
    }

    public OldMessage getMessage() {
        return message;
    }

    public OldMessage getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "MessageWithAnswer{" +
                "message=" + message +
                ", answer=" + answer +
                '}';
    }
}
