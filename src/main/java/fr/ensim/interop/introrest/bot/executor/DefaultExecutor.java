package fr.ensim.interop.introrest.bot.executor;

public class DefaultExecutor extends Executor {

    @Override
    public String execute(String[] commands) {
        return "Désolé mais ce n'est pas une action valide...";
    }
    
}
