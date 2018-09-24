package com.alex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Shishkov A.V. on 24.09.18.
 */
public class ConsumerApplication {
	private final static String QUEUE_NAME = "demo_queue";

	private static String getPrettifiedString(String jsonString)
	{
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(jsonString).getAsJsonObject();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(json);

		return prettyJson;
	}

	public static void main(String[] argv) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try {
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			DefaultConsumer consumer = new DefaultConsumer(channel) {
				private final Logger innerLogger =
						LoggerFactory.getLogger(DefaultConsumer.class);
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
						throws IOException {
					String message = getPrettifiedString(new String(body, "UTF-8"));
					System.out.println(" [x] Received:");
					System.out.println(message);
					System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
				}
			};
			channel.basicConsume(QUEUE_NAME, true, consumer);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
