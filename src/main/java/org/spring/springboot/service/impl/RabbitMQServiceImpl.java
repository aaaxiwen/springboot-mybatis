package org.spring.springboot.service.impl;

import org.spring.springboot.service.RabbitMQService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {

	
	///监听同时也消费了  所以这里只有一个方法能监听到
	@Override
	@RabbitListener(queues = "xxw.news")
	public void listener(Object object) {
		System.out.println(object.getClass());
		System.out.println(object);
	}

	@Override
	@RabbitListener(queues = "xxw.news")
	public void listener01(Message message) {
		System.out.println(message.getBody());
		System.out.println(message.getMessageProperties());
		
	}
	
	
}
