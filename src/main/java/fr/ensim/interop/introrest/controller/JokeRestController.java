package fr.ensim.interop.introrest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ensim.interop.introrest.model.Joke;

@RestController 
public class JokeRestController {

    private Map<Long, Joke> jokes = new HashMap<>();

    public JokeRestController() {
        jokes.put(new Long(1), new Joke(new Long(1), "blague1", "Mouais", 2));
        jokes.put(new Long(2), new Joke(new Long(2), "blague2", "Pas mal", 6));
    }

    public Joke getRandomJoke() {
        Object[] valuesArray = jokes.values().toArray();
        return (Joke) valuesArray[new Random().nextInt(valuesArray.length)];
    }

    @GetMapping("/joke")
    public ResponseEntity<Joke> sendJoke() {
        return ResponseEntity.ok().body(getRandomJoke());
    }

    


}
