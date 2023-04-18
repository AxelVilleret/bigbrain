package fr.ensim.interop.introrest.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import javax.swing.text.Highlighter.Highlight;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ensim.interop.introrest.model.Joke;

@RestController 
public class JokeRestController {

    private Map<Long, Joke> jokes = new HashMap<>();

    public JokeRestController() {
        jokes.put(new Long(1), new Joke(new Long(1), "Plage", "La plage dit à l'océan: dire que tout le monde aime l'eau c'est assez vague.", 3));
        jokes.put(new Long(2), new Joke(new Long(2), "Investi-gator", "Comment appelle-t-on un alligator qui enquête ? Un investi-gator.", 7));
    }

    public Joke getRandomJoke() {
        Object[] valuesArray = jokes.values().toArray();
        return (Joke) valuesArray[new Random().nextInt(valuesArray.length)];
    }

    public Joke getJokeByFilter(String filter) {
        switch (filter) {
            case "high":
                for(Entry<Long, Joke> entry: jokes.entrySet()) {
                    if (entry.getValue().getNote() >= 7) {
                        return entry.getValue();
                    }
                }
                break;
            case "low":
                for (Entry<Long, Joke> entry : jokes.entrySet()) {
                    if (entry.getValue().getNote() <= 3) {
                        return entry.getValue();
                    }
                }
                break;
            default:
                return getRandomJoke();
        }
        return getRandomJoke();
    }

    @GetMapping("/joke")
    public ResponseEntity<Joke> sendJoke(@RequestParam(name = "restriction", required = false) String restriction) {
        
        if (restriction == null) {
            return ResponseEntity.ok().body(getRandomJoke());
        }
        else {
            return ResponseEntity.ok().body(getJokeByFilter(restriction));
        }
            
    }

    


}
