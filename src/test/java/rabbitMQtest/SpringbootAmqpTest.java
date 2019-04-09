package rabbitMQtest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.springboot.Application;
import org.spring.springboot.domain.Wechatuser;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

import ch.qos.logback.core.net.SyslogOutputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringbootAmqpTest{
	@Autowired
	RabbitTemplate rabbitTemplate;
	@Autowired
	AmqpAdmin amqpAdmin;
	
	@Test
	public void contextLoads() {
		//Message需要组件构造一个;定义消息体内容和消息头
		//rabbitTemplate.send(message,routeKey,message);
		
		//object默认当成信息体,只需要传入要发送的对象,自动序列化发给rabbitmq
		//rabbitTemplate.convertAndSend(exchange, routingKey, object);
		Map<String, Object> map = new HashMap<>();
		map.put("msg", "萧喜文的信息");
		map.put("data", Arrays.asList("长得帅",132,true));
		rabbitTemplate.convertAndSend("xxw.exchange.topic", "dfdfd.news", map);
	}
	@Test
	public void receive() {
		Object object = rabbitTemplate.receiveAndConvert("xxw.news");
		System.out.println(object.getClass());
		System.out.println(object);
	}
	@Test
	public void createExchange() {
		//RabbitMQ 对于已创建的不会再创建了
		amqpAdmin.declareExchange(new TopicExchange("admin.exchange.topic"));
		amqpAdmin.declareQueue(new Queue("admin.queue", true));//true 定义这个队里是持久存活的，false定义这个队列是重启后不存在
		amqpAdmin.declareBinding(new Binding("admin.queue", Binding.DestinationType.QUEUE, "admin.exchange.topic", "admin.#", null));
	}
	@Test
	public void contextLoadsForIm() {
		Wechatuser wechatuser = new Wechatuser();
		wechatuser.setId(1l);
		wechatuser.setNikename("sdf");
		String string = String.valueOf(wechatuser.getId())+","+wechatuser.getNikename()+","+wechatuser.getAvatarurl();
		rabbitTemplate.convertAndSend("im.exchange.topic", "im.wechatuser",string);
	}
	
}
