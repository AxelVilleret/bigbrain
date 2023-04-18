package fr.ensim.interop.introrest;

import java.util.Date;
import java.util.UUID;

import fr.ensim.interop.introrest.model.AuthToken;

public class AuthManager {
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    public static AuthToken generateToken(String userId) {
        Date expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String token = UUID.randomUUID().toString();
        return new AuthToken(userId, expiryDate, token);
    }

}
