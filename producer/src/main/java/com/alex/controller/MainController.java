package com.alex.controller;

import com.alex.domain.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final String QUEUE_NAME = "demo_queue";
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	private void sendMessage(String message) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection()) {
			try (Channel channel = connection.createChannel()) {
				channel.queueDeclare(QUEUE_NAME, false, false, false, null);
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
			}
		}
	}

	@PostMapping(value = "/messages", consumes = {MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Message> getMessage(@RequestBody Message message) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String messageJson = gson.toJson(message);

		try {
			sendMessage(messageJson);
			LOGGER.info(" [x] Sent:\n" + message);
		} catch (IOException | TimeoutException e) {
			LOGGER.error(e.toString());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
