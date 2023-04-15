package fr.ensim.interop.introrest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ensim.interop.introrest.model.telegram.ApiResponseTelegram;
import fr.ensim.interop.introrest.model.telegram.ApiResponseUpdateTelegram;
import fr.ensim.interop.introrest.model.telegram.Message;
import fr.ensim.interop.introrest.model.telegram.perso.UserMessage;

@RestController
public class MessageRestController {

	@Value("${telegram.api.url}")
	private String telegramApiUrl;

	@Value("${telegram.bot.id}")
	private String telegramApiToken;

	public ApiResponseTelegram<Message> sendMessageToTelegram(UserMessage message) throws RestClientException {
		return new RestTemplate().postForObject(telegramApiUrl + telegramApiToken + "/sendMessage", message,
				ApiResponseTelegram.class);
	}

	@PostMapping("/message")
	public ResponseEntity<ApiResponseTelegram<Message>> sendMessage(@RequestBody UserMessage message) {
		try {
			ApiResponseTelegram<Message> response = sendMessageToTelegram(message);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (RestClientException e) {
			String jsonString = e.getMessage();
			System.out.println(jsonString);
			int startIndex = jsonString.indexOf("{");
			int endIndex = jsonString.lastIndexOf("}");
			String jsonArrayString = jsonString.substring(startIndex, endIndex + 1);
			ObjectMapper om = new ObjectMapper();
			ApiResponseTelegram<Message> response;
			try {
				response = om.readValue(jsonArrayString,
						om.getTypeFactory().constructParametricType(ApiResponseTelegram.class, Message.class));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} catch (JsonMappingException e1) {
				e1.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
	}
	
	@GetMapping("/update")
	public ResponseEntity<ApiResponseUpdateTelegram> getUpdates(
			@RequestParam(name = "offset", required = false) String offset) {
		RestTemplate restTemplate = new RestTemplate();
		if (offset == null) {
			return restTemplate.getForEntity(telegramApiUrl + telegramApiToken + "/getUpdates",
					ApiResponseUpdateTelegram.class);
		}
		return restTemplate.getForEntity(telegramApiUrl + telegramApiToken + "/getUpdates?offset=" + offset,
						ApiResponseUpdateTelegram.class);

	}
}
