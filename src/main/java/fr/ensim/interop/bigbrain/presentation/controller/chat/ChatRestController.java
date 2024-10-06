package fr.ensim.interop.bigbrain.presentation.controller.chat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ensim.interop.bigbrain.application.chat.GetResponseUseCase;
import fr.ensim.interop.bigbrain.domain.chat.UserMessage;

@Controller
public class ChatRestController {
    private final GetResponseUseCase getResponseUseCase;

    public ChatRestController(GetResponseUseCase getResponseUseCase) {
        this.getResponseUseCase = getResponseUseCase;
    }

    @GetMapping("/chat")
    public ResponseEntity<Map<String, String>> getResponse(
            @RequestParam("content") String content,
            @RequestParam("userId") Long userId) 
    {
        String response = getResponseUseCase.execute(new UserMessage(content, userId));
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("response", response);
        return ResponseEntity.ok().body(responseBody);
    }
}
