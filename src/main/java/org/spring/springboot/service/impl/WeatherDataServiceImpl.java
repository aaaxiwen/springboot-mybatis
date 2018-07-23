package org.spring.springboot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.domain.Weather;
import org.spring.springboot.domain.WeatherResponse;
import org.spring.springboot.service.WeatherDataService;
import org.spring.springboot.util.VCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    private final static Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private  RestTemplate getRestTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private  final String WEATHER_API = "http://wthrcdn.etouch.cn/weather_mini";

    private final Long TIME_OUT = 1800L; // 缓存超时时间

    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        String uri = WEATHER_API + "?citykey=" + cityId;
        return this.doGetWeatherData(uri);

    }

    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        String uri = WEATHER_API + "?citykey=" + cityName;
        return this.doGetWeatherData(uri);
    }

    private  WeatherResponse doGetWeatherData(String uri){
        ValueOperations<String,String> ops = this.stringRedisTemplate.opsForValue();
        String key = uri;
        String strBody = null;
        //先查缓存，没再找服务
        if(!this.stringRedisTemplate.hasKey(key)){
            logger.info("没找到key"+key);
            ResponseEntity<String> response = getRestTemplate.getForEntity(uri,String.class);
            if(response.getStatusCodeValue() == 200){
                strBody = response.getBody();
                System.out.println("反序列化之前的字符串:"+strBody);
            }
            ops.set(key,strBody,TIME_OUT, TimeUnit.HOURS);
        }else {
            logger.info("找到key"+key+",value="+ops.get(key));
            strBody = ops.get(key);
        }
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse weather = null;

        try {
            weather = mapper.readValue(strBody,WeatherResponse.class);
            System.out.println(weather);
        }catch (IOException e){
            logger.error("Json反序列化失败了",e);
            e.printStackTrace();
        }
        return weather;
    }
    
    public void dotestredisbeen() {
    	
    	Weather weather = new Weather();
    	weather.setCity("中山");
    	weather.setWendu("10度");
    	VCache.setex("test", weather, 1000000);
    	weather=(Weather) VCache.getObj("test");
    	System.out.println(weather.toString());

    }
    
}
