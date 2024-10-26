package fr.ensim.interop.bigbrain.presentation.telegramBot.executor;

public interface Executor {
    String execute(String[] message, Long userId);
}
