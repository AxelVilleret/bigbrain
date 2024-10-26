package fr.ensim.interop.bigbrain.presentation.telegramBot.executor;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import fr.ensim.interop.bigbrain.util.Constants;

@Component
public class ExecutorFactory {

    private final Map<String, Executor> executorMap = new HashMap<>();

    public ExecutorFactory
    (
            ForecastsExecutor forecastsExecutor,
            ChatExecutor chatExecutor,
            HelpExecutor helpExecutor
    ) 
    {
        executorMap.put(Constants.WEATHER_COMMAND, forecastsExecutor);
        executorMap.put(Constants.CHAT_COMMAND, chatExecutor);
        executorMap.put(Constants.HELP_COMMAND, helpExecutor);
    }

    public Executor getExecutor(String command) {
        Executor executor = executorMap.get(command.toLowerCase());
        if (executor == null) {
            throw new UnsupportedOperationException("Commande non supportée : " + command);
        }
        return executor;
    }
}