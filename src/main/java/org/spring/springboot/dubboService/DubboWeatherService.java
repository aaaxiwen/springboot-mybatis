package org.spring.springboot.dubboService;

import org.spring.springboot.domain.Weather;

public interface DubboWeatherService {
	
	public String getWeather();
	
	public Weather getWeatherEntity();
}
