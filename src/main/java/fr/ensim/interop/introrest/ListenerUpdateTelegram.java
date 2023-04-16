package fr.ensim.interop.introrest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.ensim.interop.introrest.bot.BotImpl;
import fr.ensim.interop.introrest.bot.Listener;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ListenerUpdateTelegram implements CommandLineRunner {
	
	@Override
	public void run(String... args) throws Exception {
		Logger.getLogger("ListenerUpdateTelegram").log(Level.INFO, "Démarage du listener d'updates Telegram...");
		
		new Timer().schedule(new Listener(new BotImpl()), 0, 500);
	}
}
