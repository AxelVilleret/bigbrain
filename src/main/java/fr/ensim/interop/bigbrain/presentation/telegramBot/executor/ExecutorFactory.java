package fr.ensim.interop.bigbrain.presentation.telegramBot.executor;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExecutorFactory {

    private final Map<String, Executor> executorMap = new HashMap<>();

    public ExecutorFactory(
            ForecastsExecutor forecastsExecutor,
            ChatExecutor chatExecutor) {
        executorMap.put("/meteo", forecastsExecutor);
        executorMap.put("/chat", chatExecutor);
    }

    public Executor getExecutor(String command) {
        Executor executor = executorMap.get(command.toLowerCase());
        if (executor == null) {
            throw new UnsupportedOperationException("Commande non supportée : " + command);
        }
        return executor;
    }
}