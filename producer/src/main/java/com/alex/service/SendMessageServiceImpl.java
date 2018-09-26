package com.alex.service;

import com.alex.domain.Message;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Shishkov A.V. on 24.09.18.
 */
@Service
public class SendMessageServiceImpl implements SendMessageService {
	private static final String QUEUE_NAME = "demo_queue";

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

	private String getPrettifiedMessage(Message message) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(message);
	}

	@Override
	public void send(Message message) throws IOException, TimeoutException {
		sendMessage(getPrettifiedMessage(message));
	}
}
