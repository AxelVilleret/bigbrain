package fr.ensim.interop.introrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import fr.ensim.interop.introrest.bot.BotApi;
import fr.ensim.interop.introrest.bot.BotImpl;
import fr.ensim.interop.introrest.bot.Listener;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ListenerUpdateTelegram implements CommandLineRunner {

	@Autowired
	BotApi bot;
	
	@Override
	public void run(String... args) throws Exception {
		Logger.getLogger("ListenerUpdateTelegram").log(Level.INFO, "Démarage du listener d'updates Telegram...");
		
		// new Timer().schedule(new Listener(new BotImpl()), 0, 500);

		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		botsApi.registerBot(bot);

	}
}
