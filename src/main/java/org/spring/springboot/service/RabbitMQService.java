package org.spring.springboot.service;

import org.springframework.amqp.core.Message;

public interface RabbitMQService {
	
	public void listener(Object object);
	
	public void listener01(Message message);
}
