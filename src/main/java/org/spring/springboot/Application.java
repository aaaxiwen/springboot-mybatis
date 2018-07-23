package org.spring.springboot;

import com.sun.media.jfxmedia.logging.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.spring.springboot.dao.CityDao;
import org.spring.springboot.domain.City;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot 应用启动类
 *
 * @author 萧喜文 
 * 
 */

/**
 *  redis 数据库，缓存，消息中间件（消息就算了）
 *  1引入redis的starter
 *  2配置redis
 *  	原理：CacheManager=Cache 缓存组件来实际给缓存中的存取数据
 *  	1）引入redis的starter，容器中保存的是RedisCacheManager
 *  	2) RedisCacheManager帮我们创建 RedisCache 来作为缓存组件:RedisCache通过操作redis缓存数据的
 * 		3）默认保存数据object利用jdk序列胡的，如何保存json
 * 		4）自定义CacheManager
 *  
 *  */

/** 
 * Rabbit自动配置
 * 1.RabbitAutoConfiguration
 * 2.有自动配置了连接工厂ConnectionFactory
 * 3.RabbitProperties 封装了RabbitMQ的配置
 * 4.RabbitTemplate 给RabbitMQ发送和接受信息
 * 5.AmqpAdmin RabbitMQ系统管理功能组件
 * 6.@EnableRabbit + @RabbitListener 来监听消息队列的内容
 * */

/** 
 * SpringBoot 默认支持2种技术和ES交互
 * 1.Jest(默认不生效需要导入jest包)
 * 2.SpringDate ElasticSearch
 * */

/**
 * Dubbo+zookeeper
 * 1.将服务提供者注册到注册中心
 * 		1.一如dubbo和zkclient依赖
 * 		2.配置dubbo的扫描包和注册中心地址
 * 		3.使用@Service发布服务
 *  */

// Spring Boot 应用的标识
@SpringBootApplication
@EnableScheduling//启动定时
//mapper 接口类扫描包配置
@MapperScan("org.spring.springboot.dao")
@EnableCaching//1.开启缓存基于注解 2.标注缓存注解 
@EnableRabbit//开启基于注解的RabbitMQ模式
@EnableAsync//开启异步注解   在方法上标注@Async即可spring开启线程池调用该方法
public class Application {

    public static void main(String[] args) {
        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件

        SpringApplication.run(Application.class,args);

    }
}
