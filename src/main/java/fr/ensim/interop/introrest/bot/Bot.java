package fr.ensim.interop.introrest.bot;

import fr.ensim.interop.introrest.model.telegram.Update;

public interface Bot {

    /**
     * Called when an new update is retreived.
     * 
     * @param update The update retreived.
     */
    public void onUpdatereceived(Update update);
    
} 