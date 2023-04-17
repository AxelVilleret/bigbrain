package fr.ensim.interop.introrest.controller;

import java.util.HashMap;
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
        jokes.put(new Long(1), new Joke(new Long(1), "Plage", "La plage dit à l'océan: dire que tout le monde aime l'eau c'est assez vague.", 5));
        jokes.put(new Long(2), new Joke(new Long(2), "Investi-gator", "Comment appelle-t-on un alligator qui enquête ? Un investi-gator.", 6));
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
