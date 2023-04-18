package fr.ensim.interop.introrest.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ensim.interop.introrest.AuthManager;
import fr.ensim.interop.introrest.model.AuthToken;
import fr.ensim.interop.introrest.model.Credentials;


@RestController
public class AuthController {
    private Map<String, AuthToken> tokenMap = new HashMap<>();

    @PostMapping("/auth")
    public ResponseEntity<String> authenticate(@RequestBody Credentials credentials) {
        AuthToken token = AuthManager.generateToken(credentials.getUsername());
        tokenMap.put(credentials.getUsername(), token);
        return ResponseEntity.ok().body(token.getToken());
    }

    @GetMapping("/data")
    public ResponseEntity<String> getData(@RequestHeader("Authorization") String authHeader, @RequestParam String username) throws AuthenticationException {
        String token = authHeader.substring(7); // remove "Bearer "
        AuthToken authToken = tokenMap.get(username);
        if (authToken != null && authToken.getToken().equals(token) && authToken.getExpiryDate().after(new Date())) {
            // Process request
            return ResponseEntity.ok().body("Ok");
        } else {
            return ResponseEntity.badRequest().body("Refusé");
        }
    }
}
