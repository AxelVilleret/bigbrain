package fr.ensim.interop.introrest.bot.executor;

import fr.ensim.interop.introrest.bot.BotImpl;

public class DefaultExecutor extends Executor {

    @Override
    public String execute(String[] commands) {
        return "Désolé mais ce n'est pas une action valide... Tapper " + BotImpl.NOTICE + " pour voir la notice";
    }
    
}
