package fr.ensim.interop.introrest.model;

import java.util.Date;

public class AuthToken {
    private String userId;
    private Date expiryDate;
    private String token;
    
    public AuthToken(String userId, Date expiryDate, String token) {
        this.userId = userId;
        this.expiryDate = expiryDate;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
}
