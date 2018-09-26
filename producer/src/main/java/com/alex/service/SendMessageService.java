package com.alex.service;

import com.alex.domain.Message;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Shishkov A.V. on 24.09.18.
 */
public interface SendMessageService {
	void send(Message message) throws IOException, TimeoutException;
}
