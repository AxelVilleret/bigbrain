package fr.ensim.interop.introrest.bot.executor;

import fr.ensim.interop.introrest.bot.BotImpl;

public class NoticeExecutor extends Executor {

    @Override
    public String execute(String[] commands) {
        return "Voici ce dont je suis capable :\n" +
                "- " + BotImpl.HELLO + " -> Afficher la célèbre réplique\n" +
                "- " + BotImpl.FORECASTS + " <ville> -> Afficher les prévisions pour la ville donnée pour 1 jour\n" +
                "- " + BotImpl.FORECASTS + " <ville> more -> Afficher les prévisions pour la ville donnée pour 3 jours\n" +
                "- " + BotImpl.JOKE + " -> Afficher une blague aléatoirement\n" +
                "- " + BotImpl.JOKE + " high -> Afficher une blague bien notée\n" +
                "- " + BotImpl.JOKE + " low -> Afficher une blague mal notée\n" +
                "- " + BotImpl.NOTICE + " -> Afficher l'aide\n";
    }
    
}
