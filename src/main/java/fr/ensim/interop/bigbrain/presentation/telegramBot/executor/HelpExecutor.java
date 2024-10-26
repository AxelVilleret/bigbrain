package fr.ensim.interop.bigbrain.presentation.telegramBot.executor;

import org.springframework.stereotype.Component;

@Component
public class HelpExecutor implements Executor {

    @Override
    public String execute(String[] message, Long userId) {
        return "Voici les commandes disponibles :\n" +
                "/meteo [ville] [more] - Obtenez la météo pour une ville. Ajoutez 'more' pour des prévisions sur 3 jours.\n"
                +
                "/chat [message] - Envoyez un message au bot de chat.\n" +
                "/aide - Affiche cette notice.";
    }
}