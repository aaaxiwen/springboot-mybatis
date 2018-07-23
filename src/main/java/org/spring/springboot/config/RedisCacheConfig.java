package org.spring.springboot.config;

import java.net.UnknownHostException;

import org.spring.springboot.domain.City;
import org.spring.springboot.domain.Weather;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisCacheConfig {
	@Bean
	public RedisTemplate<Object, Object> jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory)
	throws UnknownHostException{
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setConnectionFactory(redisConnectionFactory);
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		template.setDefaultSerializer(serializer);
		return template;
	}
	//CacheManagerCustomizers可以来定制缓存的规则（少人用）
	@Primary
	@Bean
	public RedisCacheManager jsonCacheManager(RedisTemplate<Object, Object> jsonRedisTemplate) {
		RedisCacheManager cacheManager =new RedisCacheManager(jsonRedisTemplate);
		//key多了一个前缀
		//默认使用CacheName作为key的前缀
		cacheManager.setUsePrefix(true);
		//设置缓存过期时间
		//cacheManager.setDefaultExpiration(60);//秒
		return cacheManager;
	}
	
	
	@Bean
	public RedisTemplate<Object, Weather> weatherRedisTemplate(RedisConnectionFactory redisConnectionFactory)
	throws UnknownHostException{
		RedisTemplate<Object, Weather> template = new RedisTemplate<Object, Weather>();
		template.setConnectionFactory(redisConnectionFactory);
		Jackson2JsonRedisSerializer<Weather> serializer = new Jackson2JsonRedisSerializer<Weather>(Weather.class);
		template.setDefaultSerializer(serializer);
		return template;
	}
	
	@Bean
	public RedisCacheManager weatherCacheManager(RedisTemplate<Object, Weather> weatherRedisTemplate) {
		RedisCacheManager cacheManager =new RedisCacheManager(weatherRedisTemplate);
		//key多了一个前缀
		//默认使用CacheName作为key的前缀
		cacheManager.setUsePrefix(true);
		return cacheManager;
	}
	
	@Bean
	public RedisTemplate<Object, City> cityRedisTemplate(RedisConnectionFactory redisConnectionFactory)
	throws UnknownHostException{
		RedisTemplate<Object, City> template = new RedisTemplate<Object, City>();
		template.setConnectionFactory(redisConnectionFactory);
		Jackson2JsonRedisSerializer<City> serializer = new Jackson2JsonRedisSerializer<City>(City.class);
		template.setDefaultSerializer(serializer);
		return template;
	}
	
	@Bean
	public RedisCacheManager cityCacheManager(RedisTemplate<Object, City> cityRedisTemplate) {
		RedisCacheManager cacheManager =new RedisCacheManager(cityRedisTemplate);
		//key多了一个前缀
		//默认使用CacheName作为key的前缀
		cacheManager.setUsePrefix(true);
		return cacheManager;
	}

}
