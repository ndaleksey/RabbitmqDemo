package com.alex.controller;

import com.alex.domain.Message;
import com.alex.service.SendMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Shishkov A.V. on 24.09.18.
 */
@RestController
public class MainController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private SendMessageService sendMessageService;

	@PostMapping(value = "/messages", consumes = {MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<String> getMessage(@RequestBody Message message) {
		try {
			sendMessageService.send(message);
			LOGGER.info(" [x] Sent:\n" + message);
		} catch (IOException | TimeoutException e) {
			LOGGER.error(e.toString());
			return new ResponseEntity<>("Error while sending message",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>("Message was sent", HttpStatus.OK);
	}
}
