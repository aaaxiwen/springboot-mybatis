package org.spring.springboot.dubboService.impl;

import org.spring.springboot.domain.Weather;
import org.spring.springboot.dubboService.DubboWeatherService;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;


@Component
@Service//service是dubbo的 将服务发布出去  方法名是根据全类名发布的
public class DubboWeatherServiceImpl implements DubboWeatherService {

	@Override
	public String getWeather() {
		return "天气很好";
	}
	
	public Weather getWeatherEntity() {
		Weather weather = new Weather();
		weather.setCity("中山市");
		return  weather;
	}

	
}
