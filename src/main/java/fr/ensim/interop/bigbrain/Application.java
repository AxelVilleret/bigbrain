package fr.ensim.interop.bigbrain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import fr.ensim.interop.bigbrain.presentation.telegramBot.MessagesHandler;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public TelegramBotsApi telegramBotsApi(MessagesHandler messagesHandler) {
		TelegramBotsApi botsApi = null;
		try {
			botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(messagesHandler);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		return botsApi;
	}
}